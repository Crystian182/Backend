package it.unisalento.se.saw.factory;

public class FactoryProducer {
	
	public static AbstractFactory getFactory(String choice){
		   
	      if(choice.equalsIgnoreCase("NOTIFICATION")){
	         return new NotificationFactory();
	      }
	      
	      return null;
	   }

}
