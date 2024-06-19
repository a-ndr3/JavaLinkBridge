using IO.Swagger.SharpModel;
using System.Diagnostics;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace JLBWPF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        UpdateManager updateManager;
        List<InstanceType> instanceTypes;
        public MainWindow()
        {
            InitializeComponent();

            updateManager = UpdateManager.Instance;
        }

        private void CreateInstance_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                var instanceType = CreateInstanceName.Text;
                var instanceName = InstanceName.Text;
                var realType = instanceTypes.FirstOrDefault(x => x.Name == instanceType);

                if (instanceType != null)
                {
                    var inst = Instance.create(realType, instanceName);
                    updateManager.instances.Add(inst.Id.Value, inst);
                    printIntoLogBox("Instance Created Successfully!");
                    printIntoLogBox(getInstanceForPrinting(inst));
                }
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void Disconnect_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Exit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Connect_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                updateManager.getInitialServerUpdates();

                instanceTypes = InstanceType.getInstanceTypes();

                if (instanceTypes != null)
                {
                    printIntoLogBox("Connected Successfully!");

                    printIntoLogBox("Avaliable Instance Types:");

                    var sb = new StringBuilder();

                    foreach (var instanceType in instanceTypes)
                    {
                        sb.Append(instanceType.Name + ", ");
                    }
                    printIntoLogBox(sb.ToString());
                }
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void DeleteInstance_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                var instanceId = DeleteInstanceID.Text;
                var instance = updateManager.instances.Values.FirstOrDefault(x => x.Id == long.Parse(instanceId));

                if (instance != null)
                {
                    if (instance.delete())
                        printIntoLogBox("Instance Deleted Successfully!");
                    else
                        printIntoLogBox("Error: Instance not deleted!");
                }
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void SetProperties_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                var instanceId = PropertyInstanceID.Text;
                var propertyName = PropertyName.Text;
                var propertyValue = PropertyValue.Text;

                var instance = updateManager.instances.Values.FirstOrDefault(x => x.Id == long.Parse(instanceId));

                if (instance != null)
                {
                    var property = instance.Properties.FirstOrDefault(x => x.Key == propertyName);

                    if (property.Key != null)
                    {
                        Property.set(property.Key, instance, propertyValue);
                        printIntoLogBox("Property Set Successfully!");
                    }
                }
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void FetchChanges_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                updateManager.getUpdates();
                printIntoLogBox("Changes Fetched Successfully!");
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void ConcludeChanges_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                updateManager.conclude();
                printIntoLogBox("Changes Concluded Successfully!");
            }
            catch (Exception ex)
            {
                printIntoLogBox("Error: " + ex.Message);
            }
        }

        private void DeleteInstanceID_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            DeleteInstanceID.Text = "";
        }

        private void PropertyInstanceID_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            PropertyInstanceID.Text = "";
        }

        private void PropertyName_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            PropertyName.Text = "";
        }

        private void PropertyValue_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            PropertyValue.Text = "";
        }

        private void CreateInstanceName_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            CreateInstanceName.Text = "";
        }

        private void printIntoLogBox(string message)
        {
            LogTextBox.Text += message + "\n";
        }

        private string getInstanceForPrinting(Instance inst)
        {
            var sb = new StringBuilder();
            sb.Append("Instance---------------->" + "\n");
            sb.Append("id: " + inst.Id + "\n");
            sb.Append("name: " + inst.Name + "\n");
            sb.Append("dto_type: " + inst.Type + "\n");
            sb.Append("properties: \n");
            foreach (var prop in inst.Properties)
            {
                sb.Append(prop.Key + ": " + prop.Value + "\n");
            }
            sb.Append("<----------------Instance" + "\n");
            return sb.ToString();
        }

        private void InstanceName_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            InstanceName.Text = "";
        }

        private void LocalButton_Click(object sender, RoutedEventArgs e)
        {
            foreach (var inst in updateManager.instances)
            {
                printIntoLogBox(getInstanceForPrinting(inst.Value));
            }
        }

        private void BridgeButton_Click(object sender, RoutedEventArgs e)
        {
            var instances = Instance.getInstances(instanceTypes.FirstOrDefault(x => x.Name == "Student"));
            foreach (var inst in instances)
            {
                printIntoLogBox(getInstanceForPrinting(inst));
            }
        }

        private void cleanLog()
        {
            LogTextBox.Text = "";
        }

        private void CleanLog_Click(object sender, RoutedEventArgs e)
        {
            cleanLog();
        }

        private async void Response_Click(object sender, RoutedEventArgs e)
        {
            cleanLog();
            printIntoLogBox("< Response Test: 1000 instances >");
            await Dispatcher.InvokeAsync(() => { }, System.Windows.Threading.DispatcherPriority.Background);

            var studentType = instanceTypes.FirstOrDefault(x => x.Name == "Student");
            var total = 0.0;
            await Task.Run(() =>
            {
                var totalDuration = 0.0;
                var stopwatch = new Stopwatch();
                for (int i = 0; i < 1000; i++)
                {
                    stopwatch.Start();
                    createInstancesWithConclude(1, studentType);
                    stopwatch.Stop();
                    totalDuration += stopwatch.Elapsed.TotalSeconds;
                    stopwatch.Reset();
                }
                total = totalDuration;
            });
            var averageTime = total / 1000;
            printIntoLogBox("< Response Test > Average time: " + averageTime);
        }

        private async void Performance_Click(object sender, RoutedEventArgs e)
        {
            cleanLog();
            printIntoLogBox("< Performance Test: 10 000 instances >");
            await Dispatcher.InvokeAsync(() => { }, System.Windows.Threading.DispatcherPriority.Background);
            var total = 0.0;
            var studentType = instanceTypes.FirstOrDefault(x => x.Name == "Student");
            await Task.Run(() =>
             {
                 var times = new List<double>();
                 var stopwatch = new Stopwatch();
                 for (int i = 0; i < 10; i++)
                 {
                     stopwatch.Start();
                     createInstancesWithConclude(1000, studentType);
                     stopwatch.Stop();
                     times.Add(stopwatch.Elapsed.TotalSeconds);
                     stopwatch.Reset();
                 }
                 total = times.Average();
             });
            printIntoLogBox("< Performance Test > Average time: " + total);
        }

        private async void Throughput_Click(object sender, RoutedEventArgs e)
        {
            cleanLog();
            printIntoLogBox("< Throughput Test: 1 minute > ");
            await Dispatcher.InvokeAsync(() => { }, System.Windows.Threading.DispatcherPriority.Background);

            int requestCount = 0; int instCount = 0;
            var startTime = DateTime.Now;
            var studentType = instanceTypes.FirstOrDefault(x => x.Name == "Student");

            var endTime = startTime.AddMinutes(1);
            await Task.Run(() =>
            {
                while (DateTime.Now < endTime)
                {
                    createInstancesWithConclude(1000, studentType);
                    requestCount++;
                    instCount += 1000;
                }
            });
            printIntoLogBox("< Throughput Test > Requests: " + requestCount + " Instances: " + instCount);
        }

        public void createInstancesWithConclude(int count, InstanceType studentType)
        {
            for (int i = 0; i < count; i++)
            {
                var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
            }
            updateManager.conclude();
        }
    }
}