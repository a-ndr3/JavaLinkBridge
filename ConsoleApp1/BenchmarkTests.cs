using IO.Swagger.SharpModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BenchmarkDotNet.Attributes;
using BenchmarkDotNet.Running;
using System.Diagnostics;

namespace IO.Swagger
{
    internal class BenchmarkTests
    {
        UpdateManager updateManager = UpdateManager.Instance;

        List<InstanceType> instanceTypes;

        InstanceType studentType;

        Stopwatch stopwatch;

        List<double> testRunTimers = new List<double>();

        public BenchmarkTests(List<InstanceType> instanceTypes, InstanceType studentType)
        {
            this.instanceTypes = instanceTypes;
            this.studentType = studentType;
        }

        [Benchmark]
        public void createInstance()
        {
            var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
        }

        [Benchmark]
        public void createInstances(int count)
        {
            for (int i = 0; i < count; i++)
            {
                var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
            }
        }

        [Benchmark]
        public void createInstancesWithConclude(int count)
        {
            for (int i = 0; i < count; i++)
            {
                var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
            }
            updateManager.conclude();
        }

        [Benchmark]
        public void createInstancesWithConclude(int count, int concludeStep)
        {
            for (int i = 0; i < count; i++)
            {
                if (i % concludeStep == 0)
                {
                    updateManager.conclude();
                }
                var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
            }
            updateManager.conclude();
        }

        public void PerformanceTest()
        {
            for (int i = 0; i < 20; i++)
            {
                startTimer();
                createInstancesWithConclude(1000);
                stopTimer();
                saveTimer();
            }
            SaveResultsIntoFile("Performance Test");
        }

        [Benchmark]
        public void ThroughputTest(int instancesAmount)
        {
            int requestCount = 0; int instCount = 0;
            var startTime = DateTime.Now;

            var endTime = startTime.AddMinutes(1);

            while (DateTime.Now < endTime)
            {
                createInstancesWithConclude(instancesAmount);
                requestCount++;
                instCount += instancesAmount;
            }

            Console.WriteLine($"Requests per minute: {requestCount} , instances created/concluded/updated per minute: {instCount}");
        }

        [Benchmark]
        public void ErrorRateTest(int instancesAmount)
        {
            int successCount = 0;
            int errorCount = 0;

            try
            {
                createInstancesWithConclude(instancesAmount);
                successCount++;
            }
            catch (Exception)
            {
                errorCount++;
            }

            Console.WriteLine($"Success: {successCount}, Errors: {errorCount}");
        }
    
        public void ResponseTimeTest(int instancesAmount)
        {
            var totalDuration = 0.0;
            for (int i = 0; i < instancesAmount; i++)
            {
                startTimer();
                createInstancesWithConclude(1);
                stopTimer();
                totalDuration += stopwatch.Elapsed.TotalSeconds;
            }

            var averageTime = totalDuration / instancesAmount;
            Console.WriteLine($"Average Response Time: {averageTime} s");          
        }

        [Benchmark]
        public void ErrorRateTest(int instancesAmount, int concludeStep)
        {
            int successCount = 0;
            int errorCount = 0;

            try
            {
                createInstancesWithConclude(instancesAmount, concludeStep);
                successCount++;
            }
            catch (Exception)
            {
                errorCount++;
            }

            Console.WriteLine($"Success: {successCount}, Errors: {errorCount}");
        }

        public void startTimer()
        {
            stopwatch = new Stopwatch();
            stopwatch.Start();
        }
        public void cleanTimer()
        {
            testRunTimers.Clear();
        }
        public void stopTimer()
        {
            stopwatch.Stop();
        }
        public void saveTimer()
        {
            testRunTimers.Add(stopwatch.Elapsed.TotalSeconds);
        }
        public void SaveResultsIntoFile(string testName)
        {
            string path = "C:\\Users\\Public\\Documents\\BenchmarkResults.txt";
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(path))
            {
                file.WriteLine($"Test run: {testName}");
                file.WriteLine("_____________________");
                foreach (var timer in testRunTimers)
                {
                    file.WriteLine(timer);
                }
            }
            cleanTimer();
        }
    }
}
