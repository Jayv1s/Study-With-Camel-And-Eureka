package com.home.example.resteasy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.home.example.resteasy.hazelcast.HazelcastHelper;

import lombok.extern.log4j.Log4j2;

@WebListener
@Log4j2
public class HazelcastStartupListener implements ServletContextListener {
	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		log.info("web application stopping");
		Hazelcast.shutdownAll();
	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		log.info("web application starting");
//		HazelcastHelper.getINSTANCE().setHz(Hazelcast.getOrCreateHazelcastInstance());
		HazelcastInstance hzi = Hazelcast.getHazelcastInstanceByName("hzInstance1");
		log.info("- 1 - hzInstance1: " + hzi);

//		log.info("- 1 - hzInstance1 client: " + HazelcastClient.newHazelcastClient());

//		HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient();
//		if (!hzInstance.getLifecycleService().isRunning()) {
		if (hzi == null) {
			hzi = Hazelcast.getOrCreateHazelcastInstance();
			log.info("- 2 - Creando: " + hzi.getName());
			log.info("- 2 - Creando: " + hzi.getCluster().getLocalMember().getSocketAddress().getHostName());
		}
		HazelcastHelper.getINSTANCE().setHz(hzi);
		log.info("- 3 - hzInstance1: " + Hazelcast.getHazelcastInstanceByName("hzInstance1"));
		log.info("- 4 - hzInstance1: " + HazelcastHelper.getINSTANCE().getHz());
//		log.info("- 2 - hzInstance1 client: " + HazelcastClient.newHazelcastClient());
	}
}