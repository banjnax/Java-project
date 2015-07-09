package com.banjo.simtest;
import eduni.simjava.*;
import eduni.simjava.distributions.*;
public class Sink extends Sim_entity{
	private Sim_port in,out1,out2;
	private Sim_normal_obj delay;
	private Sim_random_obj prob;
	
	public Sink(String name,double mean,double var){
		super(name);
		this.delay = new Sim_normal_obj("Delay", mean, var);
		prob = new Sim_random_obj("Probability");
		add_generator(delay);
		add_generator(prob);
		in = new Sim_port("In");
		out1 = new Sim_port("Out1");//for disk one
		out2 = new Sim_port("Out2");
		add_port(in);
		add_port(out1);
		add_port(out2);
	}
	public void body(){
		int i=0;
		while(Sim_system.running()){
			Sim_event e = new Sim_event();
			sim_get_next(e);
			//sim_get_next(new Sim_from_p(Sim_system.get_entity_id("Source1")), e);
			System.out.println("---------------------"+e.get_src()+"---------------");
			System.out.println("I got a job from source!job number:"+(i+1));
			sim_process(delay.sample());
			sim_completed(e);
			if(prob.sample()<0.6) {
				System.out.println("I store a job to disk1!job number:"+(i+1));
				sim_schedule(out1, 0.0, 1);//the first parameter is the destination, the second one is the delay for sending this event;
			}
			else {
				System.out.println("I store a job to disk2!job number:"+(i+1));
				sim_schedule(out2, 0.0, 1);
			}
			
			i++;
		}
	}
}
