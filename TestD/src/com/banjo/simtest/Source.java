package com.banjo.simtest;
import eduni.simjava.*;
import eduni.simjava.distributions.*;

public class Source extends Sim_entity{
	private Sim_port out;
	private Sim_negexp_obj delay;
	
	public Source(String name,double mean){
		super(name);
		out = new Sim_port("Out");
		add_port(out);
		this.delay = new Sim_negexp_obj("Delay", mean);
		add_generator(delay);
	}
	public void body(){
		for(int i=0;i<100;i++){
			//send the process a job
			sim_schedule(out,0.0,0);
			//pause
			System.out.println("I have sent a job to process!");
			sim_pause(delay.sample());
		}
	}
}
