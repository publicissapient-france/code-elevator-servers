using System;
using System.ComponentModel;

namespace csharp
{
    public class ElevatorEngine
    {
        public Command NextCommand()
        {
            Console.WriteLine("NextCommand : {0}", Command.Nothing);
            return Command.Nothing;
        }

        public void Call(int atFloor, Direction to)
        {
            Console.WriteLine("Call : ({0}, {1})", atFloor, to);
        }

        public void Go(int floorToGo)
        {
            Console.WriteLine("Go : ({0})", floorToGo);
        }

        public void UserHasEntered()
        {
            Console.WriteLine("UserHasEntered");
        }

        public void UserHasExited()
        {
            Console.WriteLine("UserHasExited");
        }

        public void Reset(string cause)
        {
            Console.WriteLine("reset({0})", cause);
        }
    }
}