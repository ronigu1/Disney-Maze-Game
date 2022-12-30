# Disney Maze Game Project :
- We created a mazeGenerator use 3 creation algorithms (EmptyMazeGenerator, SimpleMazeGenerator, MyMazeGenerator)
and created 3 search algorithms (BFS,DFS,Best First Search) on the mazes.

- By using client-server architecture and threads pool we allowed multiple clients to operate in parallel,
The clients send requests and receive responses from the servers that create mazes and solve mazes.
- To shorten the communication time, we will compress the information that passes between the client-server before sending. 
The receiving party will open the compression and enjoy the information. 
Another thing we do on the server side is to save solutions that we have already calculated, 
so that if we are asked to solve a problem that has already been solved, 
we will pull the solution from the file instead of recalculating it (using decorator pattern of streams in java).
- GUI designing (using JavaFX) : we created a desktop Application according to the MVVM architecture and OOP.
- We used Log4j2 to save logs of various operations performed on the system.



----------------------------------------------------------------
- Little about the MVVM pattern :
    The MVVM pattern helps cleanly separate an application's business and presentation logic from its user interface (UI). Maintaining a clean separation between application logic and the UI helps address numerous development issues and makes an application easier to test, maintain, and evolve. It can also significantly improve code re-use opportunities and allows developers and UI designers to collaborate more easily when developing their respective parts of an app.

    ![image](https://user-images.githubusercontent.com/81766913/210111139-b281e1dd-6ee9-4567-9c29-b2915ac3ae8d.png)

