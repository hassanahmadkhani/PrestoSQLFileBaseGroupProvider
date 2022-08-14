package io.plugin.prestosql.groupprovider.ldap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.prestosql.spi.classloader.ThreadContextClassLoader;
import io.prestosql.spi.security.GroupProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import io.airlift.log.Logger;
import io.airlift.units.Duration;


public class FileGroupProvider implements GroupProvider {

    private static final Logger log = Logger.get(FileGroupProvider.class);


    private final LoadingCache<String, Set<String>> groupListCache;
    private boolean isIgnoreReferrals = false;
    private String groupMappingFilePath = "";

    FileGroupProvider(Map<String, String> config) {
        groupMappingFilePath = config.get("file.group-file");

        this.groupListCache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.valueOf("1h").toMillis(), TimeUnit.MILLISECONDS)
                .maximumSize(1000)
                .build(CacheLoader.from(this::getFileGroups));
    }

    private Set<String> getFileGroups(String user) {

        Set<String> groupSet = new HashSet<String>();
        Scanner in = null;

        try {
            in = new Scanner(new FileReader(this.groupMappingFilePath));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.indexOf(user) >= 0)
                    groupSet.add(line.split("=")[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                /* ignore */
            }
        }

        log.info("User: [%s], Groups: %s", user, groupSet.toString());
        return groupSet;

    }

    @Override
    public Set<String> getGroups(String user) {
        try (ThreadContextClassLoader ignored = new ThreadContextClassLoader(getClass().getClassLoader())) {
            Set<String> groups = groupListCache.getUnchecked(user);
            log.debug("User: [%s], Groups: [%s]", user, groups.toString());
            return groups;
        }
    }
}

