package com.banjo.simtest;
import eduni.simjava.*;
import eduni.simjava.distributions.Sim_normal_obj;

public class Disk extends Sim_entity{
	private Sim_port in;
	private Sim_normal_obj delay;
	
	public Disk(String name,double mean,double var){
		super(name);
		this.delay = new Sim_normal_obj("Delay", mean, var);
		add_generator(delay);
		in = new Sim_port("In");
		add_port(in);
	}
	public void body(){
		while(Sim_system.running()){
			Sim_event e =new Sim_event();
			sim_get_next(e);
			sim_process(delay.sample());
			System.out.println(this.get_name()+"I receive a file from the processor!");
			sim_completed(e);
		}
	}
}
