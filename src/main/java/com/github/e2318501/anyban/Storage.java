package com.github.e2318501.anyban;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class Storage {
    private final AnyBan plugin;
    private final Gson gson = new Gson();
    private final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public <T> List<T> load(Class<T[]> clazz, String fileName) {
        File file = getFile(fileName);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                return Arrays.asList(gson.fromJson(reader, clazz));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    public <T> void saveAsync(List<T> list, String fileName) {
        saveAsync(list, fileName, false);
    }

    public <T> void saveAsync(List<T> list, String fileName, boolean pretty) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> save(list, fileName, pretty));
    }

    public <T> void save(List<T> list, String fileName) {
        save(list, fileName, false);
    }

    public <T> void save(List<T> list, String fileName, boolean pretty) {
        File file = getFile(fileName);

        file.getParentFile().mkdir();

        try (FileWriter writer = new FileWriter(file)) {
            if (pretty) {
                prettyGson.toJson(list.toArray(), writer);
            } else {
                gson.toJson(list.toArray(), writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(String fileName) {
        return new File(plugin.getDataFolder(), fileName);
    }
}
