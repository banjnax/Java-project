package com.banjo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Person {
	int loc_x;//coordinate x of a person
	int loc_y;//coordinate y of a person
	int level=0;//the level of a person stands, different level has different strategy
	double pro_foll;//the probability of following other people
	double pro_ch;//the probability of change his way to cross the street
	int deadline;//the deadline that you can bear when looking at the people who violate the rule
	Street st;//the street environment
	boolean cross=false;//your choice
	int step;//if you are old ,maybe you should walk slowly
	boolean roadChange=false;//whether you choose anther way or not
	boolean bingo = false;//whether you get your destination
	public Person(Street st,int x,int y,double foll,double ch){
		Random r = new Random();
		this.loc_x=x;
		this.loc_y=y;
		this.pro_foll=foll;
		this.pro_ch=ch;
		this.st = st;
		this.deadline = r.nextInt(Street.pn/2);
		this.step = r.nextInt(5)+1;
	}
	public void move(){
		switch(level){
			case 0:move_0();break;
			case 1:move_1();break;
			case 2:crossOver();break;
		}
	}
	public void move_0(){
		if(st.tl.status&&!roadChange){//Firstly, look at the traffic light ,if it's green while ,of course you haven't cross the street.then go though
			loc_y = loc_y -new Random().nextInt(step)-5;
			if(loc_y<420&&!cross){
				cross=true;
				st.countP++;
			}
		}
		else{
			if(!cross){//the second,if the light is red and you haven't made your mind to cross street, then you will think about it for a while
				if(st.countP>deadline) {//if your deadline is reached
					loc_y = loc_y -step;
					cross = true;
					st.countP++;
				}
				else{
					if(new Random().nextDouble()<pro_foll) {//you did a lot of  struggle in your brain
						loc_y = loc_y -step;
						cross = true;
						st.countP++;
					}
					else{//you overcome your struggle and choose to be obey the rule
						if(new Random().nextDouble()<pro_ch){//but you have other choice ,namely,you can choose other way to cross the street and maybe the light is green
//--------------------------mark-----get level one ------------------------------
							level=1;
							roadChange=true;
							//then configure next level
							step+=2;
							deadline=0;
							pro_foll+=new Random().nextDouble();
							if(pro_foll>1) pro_foll=1;
							loc_x+=step;
						}
						else{
							loc_y = loc_y -step;
							if(loc_y<420) loc_y = 420;
							if(new Random().nextBoolean()) loc_x+=5;
							else loc_x-=5;
						}
					}
				}
			}
			else{//you have bear your mind to break the rule and cross the street
				loc_y = loc_y -step;
				if(loc_y<190) level=2;
				if(new Random().nextBoolean()) loc_x+=5;
				else loc_x-=5;
			}
		}

	}
	public void move_1(){//you choose cross the street through other road
			if(loc_x>655){//-----------------------------------------mark--------------------------------------------------
				if(st.tl2.status){//Firstly, look at the traffic light ,if it's green.then go though
					loc_y = loc_y -new Random().nextInt(step)-5;
					if(loc_y<420&&!cross){
						cross=true;
					}
				}
				else{
					if(!cross){//the second,if the light is red and you haven't made your mind to cross street, then you will think about it for a while
							if(new Random().nextDouble()<pro_foll) {//you did a lot of  struggle in your brain
								loc_y = loc_y -step;
								cross = true;
								st.countP++;
							}
							else{//you overcome your struggle and choose to be obey the rule
									loc_y = loc_y -step;
									if(loc_y<420) loc_y = 420;
									if(new Random().nextBoolean()) loc_x+=5;
									else loc_x-=5;
							}
						}
					else{
						loc_y-=step;
						if(loc_y<190)  {
							level=2;
							bingo = true;
						}
					}
				}
			}
			else{//you have bear your mind to break the rule and cross the street
				loc_x+=step;
			}
	}
	public void crossOver(){
		if(bingo){
			if(new Random().nextBoolean()) loc_x+=5;
			else loc_x-=5;
		}
		else{
			loc_x = loc_x+step+3;
			if(loc_x>675) bingo = true;
		}
	}
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(loc_x-5, loc_y-5, 10, 10);
		g.setColor(c);
	}

}
