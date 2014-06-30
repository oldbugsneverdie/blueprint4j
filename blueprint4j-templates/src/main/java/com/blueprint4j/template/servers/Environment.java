package com.blueprint4j.template.servers;

import com.blueprint4j.core.app.ApplicationItem;

import java.util.ArrayList;
import java.util.List;

public class Environment extends ApplicationItem {

    List<Server> servers = new ArrayList<Server>();

    public Environment(String name) {
        super(name);
    }

    public void addServer(Server server) {
        servers.add(server);
    }

    public List<Server> getServers() {
        return servers;
    }


}
