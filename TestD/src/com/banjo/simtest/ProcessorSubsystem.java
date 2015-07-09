package com.banjo.simtest;

import eduni.simjava.Sim_system;

public class ProcessorSubsystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sim_system.initialise();
		Source  s = new Source("Source", 150.4);
		Source  s1 = new Source("Source1", 150.5);
		Sink p = new Sink("Processor",110.5,90.5);
		Disk d1 = new Disk("Disk1",130.3,65.0);
		Disk d2 = new Disk("Disk2",350.5,200.5);
		
		Sim_system.link_ports("Source", "Out", "Processor", "In");
		Sim_system.link_ports("Source1", "Out", "Processor", "In");
		Sim_system.link_ports("Processor", "Out1", "Disk1", "In");
		Sim_system.link_ports("Processor", "Out2", "Disk2", "In");
		
		Sim_system.run();
	}

}
