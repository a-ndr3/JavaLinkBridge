using IO.Swagger.Api;
using IO.Swagger.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class InstanceType
    {
        public long? Id { get; private set; }

        public string Name { get; private set; }

        private InstanceType()
        {

        }

        public static InstanceType create(Workspace workspace, string name, InstanceType instanceType, Folder folder)
        {
            InstanceTypeControllerApi instanceTypeController = new InstanceTypeControllerApi();

            var instanceTypeReq = new CreateInstanceTypeRequest(instanceType.Id, name);

            return ConvertFromDTO(instanceTypeController.CreateInstanceType(instanceTypeReq));
        }

        public void delete()
        {
            InstanceTypeControllerApi instanceTypeController = new InstanceTypeControllerApi();

            instanceTypeController.DeleteInstanceType(Id);
        }

        public static List<InstanceType> getInstanceTypes()
        {
            InstanceTypeControllerApi instanceTypeController = new InstanceTypeControllerApi();

            return instanceTypeController.GetInstanceTypes().Select(ConvertFromDTO).ToList();
        }

        private static InstanceType ConvertFromDTO(InstanceTypeDTO instanceTypeDTO)
        {
            return new InstanceType()
            {
                Id = instanceTypeDTO.Id,
                Name = instanceTypeDTO.Name
            };
        }
    }
}
