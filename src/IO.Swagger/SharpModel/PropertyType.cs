using IO.Swagger.Api;
using IO.Swagger.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class PropertyType
    {
        public long? Id { get; private set; }

        public string Name { get; private set; }

        private PropertyType()
        {

        }
      
        public static List<PropertyType> getPropertyTypes(InstanceType instanceType)
        {
            PropertyTypeControllerApi propertyTypeControllerApi = new PropertyTypeControllerApi();
            
            return propertyTypeControllerApi.GetPropertyTypes(instanceType.Id).Select(ConvertFromDTO).ToList();
        }
     
        private static PropertyType ConvertFromDTO(PropertyTypeDTO propertyTypeDTO)
        {
            return new PropertyType
            {
                Id = propertyTypeDTO.Id,
                Name = propertyTypeDTO.Name
            };
        }
    }
}
