import java.util.ArrayList;
import java.util.Iterator;


interface Subject {
    public void attach(Observer observer);
    public void detach(Observer observer);
    public void notifyObservers();
}
 
interface Observer {
    public void update(Subject subject);
}

class HelloWorldSubject implements Subject {
     
    private ArrayList<Observer> observers;
    private String str;
     
    public HelloWorldSubject() {
        super();
 
        observers = new ArrayList<Observer>();
    }
 
    public void attach(Observer observer) {
        observers.add(observer);
    }
 
    public void detach(Observer observer) {
        observers.remove(observer);
    }
 
    public void notifyObservers() {
        Iterator<Observer> iter = observers.iterator();
         
        while (iter.hasNext()) {
            Observer observer = iter.next();
            observer.update(this);
        }
    }
     
    public String getStr() {
        return str;
    }
 
    public void setStr(String str) {
        this.str = str;
        notifyObservers();
    }
}
 
class HelloWorldObserver implements Observer {
 
    public void update(Subject subject) {
        HelloWorldSubject sub = (HelloWorldSubject)subject;
        System.out.println(sub.getStr());
    }
 
}

interface Command {
    void execute();
}
 
class HelloWorldCommand implements Command {
 
    private HelloWorldSubject subject;
     
    public HelloWorldCommand(Subject subject) {
        super();
     
        this.subject = (HelloWorldSubject)subject;
    }
     
    public void execute() {
        subject.setStr("hello world");
    }
 
}

interface AbstractFactory {
    public Subject createSubject();
    public Observer createObserver();
    public Command createCommand(Subject subject);
}
 
class HelloWorldFactory implements AbstractFactory {
 
    public Subject createSubject() {
        return new HelloWorldSubject();
    }
 
    public Observer createObserver() {
        return new HelloWorldObserver(); 
    }
 
    public Command createCommand(Subject subject) {
        return new HelloWorldCommand(subject);
    }
}

class FactoryMakerSingleton {
     
    private static FactoryMakerSingleton instance = null;
    private AbstractFactory factory;
 
    private FactoryMakerSingleton() {
        factory = new HelloWorldFactory();
    }
     
    public static synchronized FactoryMakerSingleton getInstance() {
        if (instance == null) {
            instance = new FactoryMakerSingleton();
        }
         
        return instance;
    }
 
    public AbstractFactory getFactory() {
        return factory;
    }
}

public class Main {
 
    public static void main(String[] args) {
        AbstractFactory factory = FactoryMakerSingleton.getInstance().getFactory();
         
        Subject subject = factory.createSubject();
        subject.attach(factory.createObserver());
         
        Command command = factory.createCommand(subject);
         
        command.execute();
    }
 
}
