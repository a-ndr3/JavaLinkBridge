# JavaLinkBridge: .NET part

<a name="frameworks-supported"></a>
## Frameworks supported
- .NET 4.0 or later

<a name="dependencies"></a>
## Dependencies
- [RestSharp](https://www.nuget.org/packages/RestSharp) - 105.1.0 or later
- [Json.NET](https://www.nuget.org/packages/Newtonsoft.Json/) - 7.0.0 or later
- [JsonSubTypes](https://www.nuget.org/packages/JsonSubTypes/) - 1.2.0 or later

> [!WARNING]
Versions of RestSharp earlier than 110.2.0 may contain several critical bugs, including excessive socket usage, which could cause errors and prevent the creation of more than 10,000 simultaneous requests. To avoid these issues, it is recommended to upgrade to the stable version 110.2.0 before executing the generated code. Please run RESTCallsUpdater.exe immediately after RestSharp upgrading to fix the method calls.

> [!NOTE]
RESTCallsUpdater.exe requires two arguments: The first is the path to a text file containing a list of filepaths that need updating, typically including Api, Client, and Model folders. The second argument is the path to RestMethodsUpdater.txt.

<a name="getting-started"></a>
## Getting Started - default

```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class Example
    {
        public void main()
        {
            var apiInstance = new InstanceControllerApi();
            var body = new CreateInstanceRequest();

            try
            {
                InstanceDTO result = apiInstance.CreateInstance(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling InstanceControllerApi.CreateInstance: " + e.Message );
            }
        }
    }
}
```
## Getting Started with additional layer that hides calls details

```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class Example
    {
        public void main()
        {
            var updateManager = UpdateManager.Instance;

            updateManager.getInitialServerUpdates();

            var instanceTypes = InstanceType.getInstanceTypes();
            var studentType = instanceTypes.Where(x => x.Name == "Student").First(); //get id of Student instance type

            var inst = Instance.create(studentType, "student_" + Guid.NewGuid().ToString());
            updateManager.instances.Add(inst.Id.Value,inst);
        }
    }
}
```

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*InstanceControllerApi* | [**CreateInstance**](docs/InstanceControllerApi.md#createinstance) | **POST** /api/instance/create | 
*InstanceControllerApi* | [**DeleteInstance**](docs/InstanceControllerApi.md#deleteinstance) | **DELETE** /api/instance/delete/{id} | 
*InstanceControllerApi* | [**GetInstance**](docs/InstanceControllerApi.md#getinstance) | **GET** /api/instance/get/{id} | 
*InstanceControllerApi* | [**GetInstances**](docs/InstanceControllerApi.md#getinstances) | **GET** /api/instance/getInstances/{typeId} | 
*InstanceTypeControllerApi* | [**CreateInstanceType**](docs/InstanceTypeControllerApi.md#createinstancetype) | **POST** /api/instanceType/create | 
*InstanceTypeControllerApi* | [**DeleteInstanceType**](docs/InstanceTypeControllerApi.md#deleteinstancetype) | **DELETE** /api/instanceType/delete/{id} | 
*InstanceTypeControllerApi* | [**GetInstanceTypes**](docs/InstanceTypeControllerApi.md#getinstancetypes) | **GET** /api/instanceType/getTypes | 
*PropertyControllerApi* | [**GetProperties**](docs/PropertyControllerApi.md#getproperties) | **GET** /api/property/getProperties/{instanceId} | 
*PropertyControllerApi* | [**SetProperty**](docs/PropertyControllerApi.md#setproperty) | **POST** /api/property/{id}/set | 
*PropertyTypeControllerApi* | [**GetPropertyTypes**](docs/PropertyTypeControllerApi.md#getpropertytypes) | **GET** /api/propertyType/getTypes/{instanceTypeId} | 

<a name="documentation-for-models"></a>
## Documentation for Models

 - [Model.CreateInstanceRequest](docs/CreateInstanceRequest.md)
 - [Model.CreateInstanceTypeRequest](docs/CreateInstanceTypeRequest.md)
 - [Model.InstanceDTO](docs/InstanceDTO.md)
 - [Model.InstanceTypeDTO](docs/InstanceTypeDTO.md)
 - [Model.PropertyDTO](docs/PropertyDTO.md)
 - [Model.PropertyTypeDTO](docs/PropertyTypeDTO.md)
 - [Model.SetPropertyRequest](docs/SetPropertyRequest.md)

<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
