using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tornejos.Model
{
    class Modalitat
    {
        private Int64 mId;
        public Int64 Id
        {
            get { return mId; }
        }

        private string mDescription;
        public string Description
        {
            get { return mDescription; }
            set
            {
                mDescription = value;
            }
        }

        public Modalitat(Int32 mId, String mDesc)
        {
            this.Description = mDesc;
        }
    }
}
