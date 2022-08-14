package io.plugin.prestosql.groupprovider.ldap;

import io.prestosql.spi.security.GroupProvider;
import io.prestosql.spi.security.GroupProviderFactory;

import java.util.Map;

public class FileGroupProviderFactory implements GroupProviderFactory {

    @Override
    public String getName() {
        return "file";
    }

    @Override
    public GroupProvider create(Map<String, String> config) {
        if (config.isEmpty()) {
            throw new IllegalArgumentException("this group provider requires configuration properties");
        }
        return new FileGroupProvider(config);
    }
}
