using IO.Swagger.SharpModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BenchmarkDotNet.Attributes;
using BenchmarkDotNet.Running;

namespace IO.Swagger
{
    internal class BenchmarkTests
    {
        UpdateManager updateManager = UpdateManager.Instance;

        List<InstanceType> instanceTypes;

        InstanceType studentType;

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

        [Benchmark]
        public void PerformanceTest(int instancesAmount)
        {
            var startTime = DateTime.Now;

            createInstancesWithConclude(instancesAmount);

            var endTime = DateTime.Now;

            var totalDuration = (endTime - startTime).TotalMilliseconds;
            var averageTime = totalDuration / instancesAmount;

            Console.WriteLine($"Total Duration: {totalDuration} ms, Average Time: {averageTime} ms");
        }

        [Benchmark]
        public void PerformanceTest(int instancesAmount, int concludeStep)
        {
            var startTime = DateTime.Now;

            createInstancesWithConclude(instancesAmount, concludeStep);

            var endTime = DateTime.Now;

            var totalDuration = (endTime - startTime).TotalMilliseconds;
            var averageTime = totalDuration / instancesAmount;

            Console.WriteLine($"Total Duration: {totalDuration} ms, Average Time: {averageTime} ms");
        }
    }
}
