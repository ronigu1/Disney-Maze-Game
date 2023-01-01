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
---------------------------
Let's start the game :

<img width="699" alt="image" src="https://user-images.githubusercontent.com/81766913/210178424-7507b53c-7d8a-4c45-90e9-ec2d5038dbf3.png">

Selece your character : ( I will choose Ariel )
<img width="752" alt="image" src="https://user-images.githubusercontent.com/81766913/210178469-713138f3-3349-4be1-8762-f6029a209848.png">

create your maze and reaching your character friend in the other side:
<img width="649" alt="image" src="https://user-images.githubusercontent.com/81766913/210178630-9a0af6da-d13b-4b9e-bdd6-bdb4f79e1aef.png">

You did it ! ♥

<img width="644" alt="image" src="https://user-images.githubusercontent.com/81766913/210178676-a0e94b5c-8c3f-4955-8d4d-1f5b6f2e4189.png">

You won a victory video :

<img width="377" alt="image" src="https://user-images.githubusercontent.com/81766913/210178698-62db8404-459b-42d1-afa5-5a1fe3c8dc4e.png">

You can redefine the searching algorithm and the maze's generator type :

<img width="375" alt="image" src="https://user-images.githubusercontent.com/81766913/210178786-692e43d1-82f7-418a-a93b-9ff4f23cc086.png">
<img width="373" alt="image" src="https://user-images.githubusercontent.com/81766913/210178799-237f0db1-85e1-42fe-8ccf-1c8d842873be.png">

Help page :

<img width="538" alt="image" src="https://user-images.githubusercontent.com/81766913/210178876-8cad4aaa-5887-4628-997b-f79c6b0c95ca.png">



