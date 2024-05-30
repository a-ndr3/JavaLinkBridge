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
            Console.WriteLine("load. LOAD");
            Console.WriteLine("perf. PERFORMANCE");
            Console.WriteLine("thr. THROUGHPUT");
            Console.WriteLine("err. ERROR RATE");
            Console.WriteLine("res. RESOURCES");
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

                BenchmarkTests benchmarkTests = new BenchmarkTests(instanceTypes,studentType);

                //Console.WriteLine("-----------------Existing instances on Server-----------------");

                //foreach (var instance in updateManager.instances)
                //{
                //    Console.WriteLine($"Id:{instance.Id}   Name:{instance.Name}");
                //}

                while (true)
                {
                    PrintMenuConnected();

                    string optionConnected = Console.ReadLine();

                    if (optionConnected == "1")
                    {
                        Console.Clear();
                        updateManager.instances.Add(Instance.create(studentType, "student_" + Guid.NewGuid().ToString()));
                        Console.WriteLine("Instance created");

                    }
                    else if (optionConnected == "2")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        updateManager.instances.Where(x => x.Id == long.Parse(Console.ReadLine())).First().delete();
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
                            Console.WriteLine($"Id:{instance.Id}   Name:{instance.Name}");
                        }
                    }
                    else if (optionConnected == "5")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        var instance = updateManager.instances.Where(x => x.Id == long.Parse(Console.ReadLine())).First();
                        var instanceProperties = Property.getProperties(instance);

                        instanceProperties.ForEach(x => Console.WriteLine($"Name:{x.Name}   Value:{x.Id}"));

                        Console.WriteLine("Property name:");
                        var property = instanceProperties.Where(x => x.Name == Console.ReadLine()).First();
                        Console.WriteLine("Property value:");
                        Property.set(property, instance, Console.ReadLine());
                    }
                    else if (optionConnected == "6")
                    {
                        Console.Clear();
                        Console.WriteLine("Instance id:");
                        var instance = updateManager.instances.Where(x => x.Id == long.Parse(Console.ReadLine())).First();
                        var instanceProperties = Property.getProperties(instance);

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

                    }
                    else if (optionConnected == "load")
                    {
                       
                    }
                    else if (optionConnected == "perf")
                    {
                        Console.WriteLine("Performance test started: 1000");
                        benchmarkTests.PerformanceTest(1000);

                        Console.WriteLine("Performance test started: 10000");
                        benchmarkTests.PerformanceTest(10000);

                        Console.WriteLine("Performance test started: 15000");
                        benchmarkTests.PerformanceTest(15000);

                        Console.WriteLine("Performance test started: 1000, 100");
                        benchmarkTests.PerformanceTest(1000, 100);

                        Console.WriteLine("Performance test started: 10000, 100");
                        benchmarkTests.PerformanceTest(10000, 100);

                        Console.WriteLine("Performance test started: 15000, 100");
                        benchmarkTests.PerformanceTest(15000, 100);
                    }
                    else if (optionConnected == "thr")
                    {
                        benchmarkTests.ThroughputTest(1000);
                    }
                    else if (optionConnected == "err")
                    {
                        Console.WriteLine("Error rate test started: 1000");
                        benchmarkTests.ErrorRateTest(1000);

                        Console.WriteLine("Error rate test started: 10000");
                        benchmarkTests.ErrorRateTest(10000);

                        Console.WriteLine("Error rate test started: 15000");
                        benchmarkTests.ErrorRateTest(15000);

                        Console.WriteLine("Error rate test started: 1000, 100");
                        benchmarkTests.ErrorRateTest(1000,100);

                        Console.WriteLine("Error rate test started: 10000, 100");
                        benchmarkTests.ErrorRateTest(10000, 100);
                    }
                    else if (optionConnected == "res")
                    {
                        Console.WriteLine("Creating 1000 instances, conclude, update: ");
                        Stopwatch sw;
                        sw = new Stopwatch();
                        try
                        {                          
                            sw.Start();
                            for (int i = 0; i < 1; i++)
                            {
                                benchmarkTests.createInstancesWithConclude(1000);
                            }
                            sw.Stop();
                        }
                        catch (Exception e)
                        {
                            sw.Stop();
                            Console.WriteLine("Time elapsed: " + sw.Elapsed.TotalSeconds);
                            Console.WriteLine(e.Message);
                        }
                        Console.WriteLine("Time elapsed: " + sw.Elapsed.TotalSeconds);
                    }
                }
            }
            else if (option == "2")
            {
                Console.Clear();
                return;
            }
        }
    }
}
