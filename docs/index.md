# Team 5's game project

Welcome to our group project home page where we hope you can find all that you are looking for. 
Below we have posted all the links needed to find the different parts of our project. 
In addition we have also provided the game files for you to play.

## Quick Navigation

- [Go to Project Plan Evolution](#project-plan-evolution)
- [Go to Architecture Evolution](#architecture-evolution)

## Project documents

- Requirements document (enter file here)
- Architecture document (enter file here)
- [Method selection and planning document](Method_selection_and_planning.pdf)
- Risk assessment and mitigation document (enter file here)
- Implementation document (enter file here)

## Executable code 
- Download game.jar (enter file here)

## Source files for the game
- Github repository (enter link here)

--------------------------------------------------------
## Project Plan Evolution 

Below are the key versions of our project plan, showing how our Gantt chart developed throughout the course of this project

---

### **Intial  Plan - Week 1**
> *Created ealry on to outline the overall project structure*

<img width="656" height="485" alt="Screenshot 2025-10-14 at 17 30 16" src="https://github.com/user-attachments/assets/3a417c37-b6d1-46d9-9cbb-e7c56a73138d" />

**Notes:**
- Followed a **linear layout** mirroring the assessment’s structure.  
- Provided a clear high-level overview of project phases. 
- Served as the foundation for later revisions.

---
### **Revised Plan – Week 3**
> *Tasks were specified in more detail and dependencies introduced.*

<img width="719" alt="Week 3 Plan Screenshot" src="https://github.com/user-attachments/assets/390f6b7f-c357-4fc8-8591-9b2e3ffd9ef0" />

**Changes:**  
- Introduced **dependencies** between tasks (e.g., interview → requirements → planning).  
- Improved **timing estimates** based on early experience.  

---

### **Updated Plan – Week 4**
> *Dependencies grew, and parallel work began appearing.*

<img width="688" alt="Week 4 Plan Screenshot" src="https://github.com/user-attachments/assets/11004f59-ec6c-49e4-bbfa-d2c114e6953b" />

**Changes:**  
- Began **parallel documentation and design tasks**.  
- Dependencies became more complex as the team balanced different deliverables.  
- Minor formatting issues (e.g., task naming) still to be refined.

---

### **Dual Plans – Week 5**
> *Separate plans for implementation and documentation introduced for greater clarity.*

<p float="left">
  <img width="49%" alt="Week 5 Implementation Plan" src="https://github.com/user-attachments/assets/7edbe4c2-a76e-4838-954a-e20e240452bf" />
  <img width="49%" alt="Week 5 Documentation Plan" src="https://github.com/user-attachments/assets/36e4d38a-e3a8-4c67-8d65-ac6f7c72693e" />
</p>

**Changes:**  
- Implementation required **tighter coordination** between developers.  
- Focus shifted toward **game building** with more realistic, time-constrained goals.  
- Website and documentation tasks continued in parallel.

---

### **Final Plan – Week 6**
> *Reflects final adjustments, extended implementation, and inclusion of review tasks.*

<img width="970" alt="Final Plan Screenshot" src="https://github.com/user-attachments/assets/a85dbc7a-ad62-435b-a94a-82552de4dc46" />

**Final Adjustments:**  
- Implementation timeline **extended** due to unforeseen debugging needs.  
- Added **review and documentation** tasks near the end.  
- Represents a **realistic and adaptive final plan**, balancing scope and deadlines.

---------------------------------------------

## Architecture Evolution  

This section displays the progression of the game’s architecture — from early planning through the use of CRC cards, to the initial class structures and finally to the implementation of layered packages.

---

### CRC Cards — Initial Class Design  

Early CRC (Class–Responsibility–Collaborator) cards were made to get a first look of the initial class structure as well as the relationships between components.

<div align="center">
  <img src="https://github.com/user-attachments/assets/b865d48f-304e-47f1-b592-e217a59b5d41" width="45%" alt="CRC Card 1" />
  <img src="https://github.com/user-attachments/assets/4bd6c4dd-6107-47c7-9db2-c348d6e3a318" width="45%" alt="CRC Card 2" /><br/>
  <img src="https://github.com/user-attachments/assets/e4be726a-7dc4-4919-9ee9-2c81d3f79460" width="45%" alt="CRC Card 3" />
  <img src="https://github.com/user-attachments/assets/2185fdae-2705-4c98-8cb4-796575cf5c37" width="45%" alt="CRC Card 4" /><br/>
  <img src="https://github.com/user-attachments/assets/2cf48a75-6124-417e-8ef1-af687f4bac31" width="50%" alt="CRC Card 5" />
</div>

---

### Initial Architecture Structure  

The first architectural diagrams were created to illustrate the early CRC designs, in which the disgins were translated into a working code structure.  

<div align="center">
  <img src="https://github.com/user-attachments/assets/0d23854f-b921-4b20-83f2-f0442c838d7f" width="45%" alt="Architecture 1" />
  <img src="https://github.com/user-attachments/assets/eb2802db-e3ea-481a-acdc-aab0b9e00ca9" width="45%" alt="Architecture 2" /><br/>
  <img src="https://github.com/user-attachments/assets/8f05224f-8f05-407d-8080-f6322b0ba7e1" width="45%" alt="Architecture 3" />
  <img src="https://github.com/user-attachments/assets/cf4da9c2-8bea-4dc1-bb72-0d0b11404ade" width="45%" alt="Architecture 4" /><br/>
  <img src="https://github.com/user-attachments/assets/a329dca9-08af-48d9-b695-9cff15a3960c" width="25%" alt="Architecture 5" />
</div>

---

### First Implementation — Basic Packages and Layers  

The first proper implementation of an architecture structure introduced **basic package separation** and **initial architectural layering**, in which classes were organised for improved maintainability and modular design.

<div align="center">
  <img src="https://github.com/user-attachments/assets/9adea450-1213-4c4e-b6f2-d9547f6ba9d8" width="70%" alt="First Layered Implementation" />
</div>

---

### Refined Architecture — Added Layers and Use of Interfaces  

The second implementation improved the structure by:
- Adding **additional layers**
- Refactoring **Event** into an **interface** for more flexible event handling  


<div align="center">
  <img src="https://github.com/user-attachments/assets/1924fdae-6ada-4640-aa17-a8f148f57866" width="70%" alt="Refined Architecture Diagram" />
</div>

---

