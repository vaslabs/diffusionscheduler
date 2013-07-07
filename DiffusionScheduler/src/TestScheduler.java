class TestScheduler extends Thread {

    public static void main(String [] args) {
        Scheduler randomSched =  new RandomScheduler();
        Scheduler diffSched = new DiffusionODEScheduler(new RingDiffusionFunction(16));
        Scheduler robinSched = new RoundRobinScheduler();
        Scheduler priorSched = new PriorityScheduler();
		//new RoundRobinScheduler(); 
		//new RandomScheduler() ;
		// or new RoundRobinScheduler() - when written
		// or new PriorityScheduler()  - when written
        int numberOfProcesses = Integer.parseInt(args[0]);
        Process[] processes = new Process[numberOfProcesses];
        for (int i = 0; i < processes.length; i++)
        	processes[i] = new Process(i, i%141);
       
        for (int i = 0; i < processes.length; i++)
        	randomSched.addProcess(processes[i]);
        
        System.out.println("RANDOM SCHEDULING");
        randomSched.run();
        
        for (int i = 0; i < processes.length; i++)
        {
        	processes[i].reset();
        	robinSched.addProcess(processes[i]);
        }
        System.out.println("ROUND ROBIN SCHEDULING");        
        robinSched.run();
        
        for (int i = 0; i < processes.length; i++)
        {
        	processes[i].reset();
        	priorSched.addProcess(processes[i]);
        }
        System.out.println("Priority SCHEDULING");
        priorSched.run();
        
        for (int i = 0; i < processes.length; i++)
        {
        	processes[i].reset();
        	diffSched.addProcess(processes[i]);
        }
        System.out.println("DIFFUSION INSPIRED SCHEDULING");
        diffSched.run();
        
        
    }

}
