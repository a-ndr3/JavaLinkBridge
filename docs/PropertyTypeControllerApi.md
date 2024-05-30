# IO.Swagger.Api.PropertyTypeControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**GetPropertyTypes**](PropertyTypeControllerApi.md#getpropertytypes) | **GET** /api/propertyType/getTypes/{instanceTypeId} | 

<a name="getpropertytypes"></a>
# **GetPropertyTypes**
> List<PropertyTypeDTO> GetPropertyTypes (long? instanceTypeId)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetPropertyTypesExample
    {
        public void main()
        {
            var apiInstance = new PropertyTypeControllerApi();
            var instanceTypeId = 789;  // long? | 

            try
            {
                List&lt;PropertyTypeDTO&gt; result = apiInstance.GetPropertyTypes(instanceTypeId);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling PropertyTypeControllerApi.GetPropertyTypes: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **instanceTypeId** | **long?**|  | 

### Return type

[**List<PropertyTypeDTO>**](PropertyTypeDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
