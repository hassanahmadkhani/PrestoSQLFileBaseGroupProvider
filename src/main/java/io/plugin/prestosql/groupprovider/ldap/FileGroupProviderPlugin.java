package io.plugin.prestosql.groupprovider.ldap;

import io.prestosql.spi.Plugin;
import io.prestosql.spi.security.GroupProviderFactory;

import java.util.Collections;

public final class FileGroupProviderPlugin implements Plugin {

    @Override
    public Iterable<GroupProviderFactory> getGroupProviderFactories() {
        return Collections.singletonList(new FileGroupProviderFactory());
    }
}
