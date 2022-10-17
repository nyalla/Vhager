This is example project to demonstrate the Producer-Consumer pattern with BlockingQueue Data structure

Problem Statement : System has to write to database asynchrounously by reading the data from big fat file. 
Usecase: There are 2 stages for the above problem. Reading data from file and writing the data to database.

As this is a file operation we need to read the file synchronously to avoid duplicate reading. i.e., we have to use single producer.

As writing data into database is unordered, we can use async consumer to do this.

Approach:

Producer : 1
Consumer : n

	BlockingQueue is the mediator between Producer and Consumer. We are using LinkedBlockingQueue implementation of BlockingQueue.
		Methods: 
				put() --> to put an object into the queue., Producer task
				take() --> to pull an object from teh queue., COnsumer task
				
				Above methods will take care of the synchornisation among the threads. 
				
QueueWorker class: This class is the each work unit travels between P and C. This class holds the list of records to be written into the database. This class also contains the insert() method which basically have an opertion on the list of records., in our case writing into Database.

WrongTurn: Important thing to handle here is releasing threads once the consumers are done with their tasks and no more items left in the queue. For this we are using pattern called, POSION PILL, which tells the consumers to suicide once the execution is finished thus releasing the threads.

    Notes:

With 8 threads: 10002640 records

Start2022-10-08T17:25:21.156462700Z
2022-10-08T17:30:32.142113600Z

With 1 thread: for 50 laksh record
Start2022-10-08T17:33:32.292395900Z
2022-10-08T17:46:17.240752700Z
