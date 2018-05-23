using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tornejos.Model
{
    class Inscrit
    {
        /*soci_id int(11) NOT NULL,
          torneig_id int(11) NOT NULL,
          grup_num int(11),
          data timestamp NOT NULL,*/

        public Inscrit(Soci mSoci, Torneig mTorneig, Grup mGrup, DateTime mData)
        {
            this.Soci = mSoci;
            this.Torneig = mTorneig;
            this.Grup = mGrup;
            this.Data = mData;
        }

        public Inscrit(Soci mSoci, Torneig mTorneig, DateTime mData)
        {
            this.Soci = mSoci;
            this.Torneig = mTorneig;
            this.Data = mData;
        }

        private Soci mSoci;
        public Soci Soci
        {
            set { mSoci = value; }
            get { return mSoci; }
        }

        private Torneig mTorneig;
        public Torneig Torneig
        {
            set { mTorneig = value; }
            get { return mTorneig; }
        }

        private Grup mGrup;
        public Grup Grup
        {
            set { mGrup = value; }
            get { return mGrup; }
        }


        private DateTime mData;
        public DateTime Data
        {
            set { mData = value; }
            get { return mData; }
        }


    }
}
