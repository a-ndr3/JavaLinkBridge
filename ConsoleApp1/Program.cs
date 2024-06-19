using IO.Swagger;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;
using IO.Swagger.SharpModel;
using System.Diagnostics;

namespace ConsoleApp1
{
    internal class Program
    {
        private static void PrintMenuStart()
        {
            Console.WriteLine("_________________________");
            Console.WriteLine("Choose an option:");
            Console.WriteLine("1.Connect");
            Console.WriteLine("2.Exit");
            Console.WriteLine("_________________________");
        }

        private static void PrintMenuConnected()
        {
            Console.WriteLine("_________________________");
            Console.WriteLine("Choose an option:");
            Console.WriteLine("_________________________");
            Console.WriteLine("1.Create Instance");
            Console.WriteLine("2.Delete Instance");
            Console.WriteLine("3.Get Instance");
            Console.WriteLine("4.Get All Instances From Bridge");
            Console.WriteLine("40. Get All Instances LOCAL");
            Console.WriteLine("5.Set Property");
            Console.WriteLine("6.Get Property");
            Console.WriteLine("7.Conclude");
            Console.WriteLine("77.FETCH");
            Console.WriteLine("8.Disconnect");
            Console.WriteLine("________TESTS_________");
            Console.WriteLine("resp. RESPONSE TIME");
            Console.WriteLine("perf. PERFORMANCE");
            Console.WriteLine("thr. THROUGHPUT");
            Console.WriteLine("_________________________");
        }

        static void Main(string[] args)
        {
            PrintMenuStart();
            string option = Console.ReadLine();

            if (option == "1")
            {
                var updateManager = UpdateManager.Instance;

                updateManager.getInitialServerUpdates();

                var instanceTypes = InstanceType.getInstanceTypes();

                var studentType = instanceTypes.Where(x => x.Name == "Student").First();

                BenchmarkTests benchmarkTests = new BenchmarkTests(instanceTypes, studentType);

                while (true)
                {
                    PrintMenuConnected();

                    string optionConnected = Console.ReadLine();

                    if (optionConnected == "1")
                    {
                        Console.Clear();
                        var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
                        updateManager.instances.Add(inst.Id.Value, inst);


                    }
                    else if (optionConnected == "2")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        updateManager.instances.Remove(long.Parse(Console.ReadLine()));
                    }
                    else if (optionConnected == "3")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        var instance = Instance.getInstance(long.Parse(Console.ReadLine()));
                        Console.WriteLine($"Id:{instance.Id}   Name:{instance.Name}");
                    }
                    else if (optionConnected == "4")
                    {
                        Console.Clear();
                        foreach (var instance in Instance.getInstances(studentType))
                        {
                            Console.WriteLine($"Id:{instance.Id}   Name:{instance.Name}");
                        }
                    }
                    else if (optionConnected == "40")
                    {
                        Console.Clear();
                        foreach (var instance in updateManager.instances)
                        {
                            Console.WriteLine($"Id:{instance.Key}   Name:{instance.Value.Name}");
                        }
                    }
                    else if (optionConnected == "5")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        var id = long.Parse(Console.ReadLine());
                        var instance = updateManager.instances.Where(x => x.Key == id).First();
                        var instanceProperties = Property.getProperties(instance.Value);

                        instanceProperties.ForEach(x => Console.WriteLine($"Name:{x.Name}   Value:{x.Id}"));
                        //todo
                        
                        //Console.WriteLine("Property name:"); 
                        //var property = instanceProperties.Where(x => x.Name == Console.ReadLine()).First();
                        //Console.WriteLine("Property value:");
                        //Property.set(property, instance.Value, Console.ReadLine());
                    }
                    else if (optionConnected == "6")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        var instance = updateManager.instances.Where(x => x.Key == long.Parse(Console.ReadLine())).First();
                        var instanceProperties = Property.getProperties(instance.Value);

                        foreach (var pr in instanceProperties)
                        {
                            Console.WriteLine($"Name:{pr.Name}   Value:{pr.Id}");
                        }

                        Console.WriteLine("Property name:");
                        var property = instanceProperties.Where(x => x.Name == Console.ReadLine()).First();
                    }
                    else if (optionConnected == "7")
                    {
                        Console.Clear();
                        updateManager.conclude();
                    }
                    else if (optionConnected == "77")
                    {
                        Console.Clear();
                        updateManager.getUpdates();
                    }
                    else if (optionConnected == "8")
                    {
                        return;
                    }

                    else if (optionConnected == "resp")
                    {
                        benchmarkTests.ResponseTimeTest(1000);
                    }
                    else if (optionConnected == "perf")
                    {
                        Console.WriteLine("Performance test with 20 000 instances, each 1000 = conclude: ");

                        benchmarkTests.PerformanceTest();

                        Console.WriteLine("Test finished. Results are saved!");
                    }
                    else if (optionConnected == "thr")
                    {
                        benchmarkTests.ThroughputTest(1000);
                    }
                }
            }
            else if (option == "2")
            {
                Console.Clear();
                return;
            }
        }
        private static void printInstance(Instance instance)
        {
            Console.WriteLine($"Id:{instance.Id}   Name:{instance.Name}");
            foreach (var prop in instance.Properties)
            {

            }
        }
    }
}
