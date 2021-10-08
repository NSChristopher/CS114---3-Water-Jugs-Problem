import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

public class WaterTransfer {

    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        int a = scnr.nextInt();
        int b = scnr.nextInt();
        int c = scnr.nextInt();
        int goal = scnr.nextInt();

        WaterTransfer wt = new WaterTransfer();
        wt.waterTransfer(a,b,c,goal);

    }

    public void waterTransfer(int a, int b, int c, int goal){

        //store capacities and current volumes
        int[] cap = new int[] {a, b, c};
        int[] vol = new int[] {a, 0, 0};

        Queue<State> q = new LinkedList<State>();
        Stack<State> st = new Stack<State>();
        ArrayList<State> visited = new ArrayList<State>();

        
        State start = new State(vol); //Init state
        start.setCapacities(cap); //static cap

        visited.add(start);
        q.add(start);
        st.add(start);

        int breakpoint = 100000;
        while(q.peek() != null)
        {
            State next = q.poll();
            if (next.solved(goal))
            {
                for (State x = next; x != null; x = x.pred)
                    st.push(x);
                    for (State y = next; y != null; y = y.pred)
                    {
                        st.pop().display();
                    }
                    break;
            }
            if(breakpoint==0)
                break;
            for(int i = 0; i <= 2; i++)
                for(int j = 0; j <= 2; j++)
                {
                    if(j == i) continue;
                        State p = next.pour(i, j);
                    if(p == null || visited.contains(p)) continue;
                        visited.add(p);
                    q.add(p);
                }
        }
    }

    public int[] capacities;

    class State
    {

        int[] volumes;
        State pred;
        final void setCapacities(int[] c)
        {
            capacities = c;
        }
        State(int[] v)
        {
            volumes = v;
        }
        public State(State state)
        {
            volumes = new int[3];
            volumes[0] = state.volumes[0];
            volumes[1] = state.volumes[1];
            volumes[2] = state.volumes[2];
        }
        State pour(int from, int to)
        {
            State newState = new State(this);
            if((volumes[from] == 0) || (volumes[to] == capacities[to])==true)
            return null;
            else
            {
                int diff = capacities[to] - newState.volumes[to];
                if(newState.volumes[from] >= diff)
                {
                    newState.volumes[to] += diff;   
                    newState.volumes[from] -= diff;   
                }
                else
                {
                    if(newState.volumes[to] + newState.volumes[from] > capacities[to]) return null;
                        newState.volumes[to] += newState.volumes[from];
                    newState.volumes[from] = 0;
                }
            }
            newState.pred = this;
            return newState;
        }

        boolean solved(int d)
        {
            boolean result = false;
            for (int v : volumes) {
                if(v==d)
                    result = true;
            }
            return result;
        }
        void display()
        {
            System.out.println(volumes[0] + " " + volumes[1] + " " + volumes[2]);
        }
    }
}