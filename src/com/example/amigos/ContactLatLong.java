package com.example.amigos;

public class ContactLatLong {
	static int count=0;
	static String[] phonenum;
	static double[] latitude;
	static double[] longitude;
	static String mynum;
	int counter;
	static double mylat;
	static double mylong;
	static double[] ranges;
	ContactLatLong(){
		
		
	}
	ContactLatLong(String[] a,String[] b,String[] c){
	
		phonenum=a;
		mynum=App.data;
		mylat=App.lat;
		mylong=App.lng;
		count=a.length;
		latitude=new double[b.length];
		longitude=new double[c.length];
		for(int i=0;i<b.length;i++){
			latitude[i]=Double.parseDouble(b[i]);
			//System.out.println(latitude[i]);
			longitude[i]=Double.parseDouble(c[i]);
		}
	}
	public static void fndrange(){
		 Contacts J = new Contacts(1, mynum , mylat , mylong);
			LL contactlist = new LL(J);
			Contacts friends[]=new Contacts[count];
			ranges=new double[count];
			for(int i=0;i<count;i++){
				
				 friends[i] = new Contacts(i+1, phonenum[i] , latitude[i] , longitude[i]);
				contactlist.add(friends[i]);
				ranges[i]=friends[i].range;
				//friends[i].
			}
			
	}
	
}
class Contacts
{
int id;
double latitude,longitude,range,lat,lng;
String number;
public static  Contacts ME;

       public Contacts()
	{	
 
    id=0;
    number="";
		latitude=0;
		longitude=0;
		range=0;
	}
	public Contacts(int tid , String no , double lat , double lon)
	{
		this.id=tid;
		this.number=no;
		this.latitude=lat;
		this.longitude=lon;
		this.range=0;	
	}
	public Contacts(Contacts C)
	{
		this.id=C.id;
		this.number=C.number;
		this.latitude=C.latitude;
		this.longitude=C.longitude;
		this.range=C.range;	
	}
	public void copy( Contacts C)
	{
		this.id = C.id;
		this.latitude = C.latitude;
		this.longitude = C.longitude;
		this.number = C.number;
		this.range = C.range;	
	}
	
	public void findrange()
	{     lat=Math.toRadians(ME.latitude);    
      //  ME.latitude=Math.toRadians(ME.latitude);
        latitude=Math.toRadians(latitude);
        lng=Math.toRadians(ME.longitude);
       // ME.longitude=Math.toRadians(ME.longitude);
        longitude=Math.toRadians(longitude);
        double difflat = Math.abs(lat-latitude);
       //double difflat = Math.toRadians(Math.abs(ME.latitude-latitude));
       //double difflon = Math.toRadians(Math.abs(ME.longitude-longitude));
       double difflon = Math.abs(lng-longitude);
	//double x = difflon * Math.cos((ME.latitude+latitude)/2);
      double x = difflon * Math.cosh((lat+latitude)/2);
       range = Math.sqrt(difflat*difflat + x*x)*6371;
       
       
      System.out.println("range "+range+"  latitude "+latitude+" ME "+ME.latitude); 
      // latitude=0;
      // longitude=0;

	}
      
}	
		 
		


class Node
{
	Contacts con;
	Node next;
}


class LL
{
public Node head = new Node();
public int size = 0;


	public LL( Contacts A)
	{Contacts.ME=new Contacts(A);
	//Contacts.ME.copy(A);
	head=null;
	}
  public LL(){}

	public void add( Contacts tmp )
	{	
		tmp.findrange();
     //System.out.println("* "+tmp.id+" * "+tmp.range+" * "+tmp.latitude+" * "+tmp.longitude);
               Node temp = new Node();
               temp.con = new Contacts(tmp);
              
      // tmp.range=0;        
		if (head == null)
		head = temp;
		else
		{
			temp.next = head;
			head = temp;
		}
    		size++;
//System.out.println(head.con.id);
	}


	public void delete()
	{
		if (head!=null)
		{	
			head = head.next;
			size--;
		}
	}

	
	public void sort()
	{
	
	Node first = new Node();
	Node LOL = new Node();
	LOL = head;
	Node tmp = new Node();
	
	for (int i = 0 ; i < size -1 ; i++ )
	{
		first = LOL;
		LOL = LOL.next;	
		for (int j = i ; j < size - i - 1 ; j++ )
		{
		
			if ((first.con.range) < ((first.next).con.range))
			{
				//tmp.con.copy(first.con);
           tmp.con=new Contacts(first.con);
				//first.con.copy(first.next.con);
           first.con=new Contacts(first.next.con);
				//(first.next).con.copy(tmp.con);
           first.next.con=new Contacts(tmp.con);
			}
		first = first.next;
		}
	}
	}
 // LL range1;
	public void ftw()
	{
 // this.sort();
	Node first = new Node();
    first = head;
	int counter = 0 ;
		while ( first!= null)
		{
		if ( first.con.range > 2)
		counter++;
		first = first.next;
		}
   
	for (int i=0; i < counter; i++){
	
    
  delete();
  }
 	}
}
