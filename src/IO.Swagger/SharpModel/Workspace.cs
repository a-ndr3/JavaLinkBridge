using IO.Swagger.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.SharpModel
{
    public class Workspace
    {
        public long? Id { get; private set; }

        public string Name { get; private set; }

        private Workspace()
        {

        }
    }
}
