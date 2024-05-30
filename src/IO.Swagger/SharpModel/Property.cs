using IO.Swagger.Api;
using IO.Swagger.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class Property
    {
        public long? Id { get; private set; }

        public string Name { get; private set; }

        private Property()
        {

        }

        public static void set(Property property, Instance instance, Object value)
        {
            var controller = new PropertyControllerApi();

            var request = new SetPropertyRequest(property.Id, value);

            var response = controller.SetPropertyWithHttpInfo(request, instance.Id);

            if (response.StatusCode == 200)
            {
                instance.Name = value.ToString();
            }
        }

        public static List<Property> getProperties(InstanceType instanceType)
        {
            PropertyControllerApi propertyController = new PropertyControllerApi();

            return propertyController.GetProperties(instanceType.Id).Select(ConvertFromDTO).ToList();
        }

        public static List<Property> getProperties(Instance instance)
        {
            PropertyControllerApi propertyController = new PropertyControllerApi();

            return propertyController.GetProperties(instance.Id).Select(ConvertFromDTO).ToList();
        }

        private static Property ConvertFromDTO(PropertyDTO propertyDTO)
        {
            return new Property
            {
                Id = propertyDTO.Id,
                Name = propertyDTO.Name
            };
        }
    }
}
