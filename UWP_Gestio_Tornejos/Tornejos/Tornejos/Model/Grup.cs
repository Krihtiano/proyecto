using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tornejos.DDBB;

namespace Tornejos.Model
{
    public class Grup
    {
        /*
         * num int(11) NOT NULL,
          description varchar(60) NOT NULL,
          caramboles_victoria int(11) NOT NULL,
          limit_entrades int(11) NOT NULL,
          torneig_id int(11) NOT NULL,
        */

        public Grup(Int32 mNum, String mDescription, Int32 mCaramboles_victoria, Int32 mLimit_entrades, Torneig mTorneig)
        {
            this.Num = mNum;
            this.Description = mDescription;
            this.Caramboles_victoria = mCaramboles_victoria;
            this.Limit_entrades = mLimit_entrades;
            this.Torneig = mTorneig;
        }

        private Int32 mNum;
        public Int32 Num
        {
            set { mNum = value; }
            get { return mNum; }
        }

        private string mDescription;
        public string Description
        {
            get { return mDescription; }
            set { mDescription = value; }
        }

        private Int32 mCaramboles_victoria;
        public Int32 Caramboles_victoria
        {
            set { mCaramboles_victoria = value; }
            get { return mCaramboles_victoria; }
        }

        private Int32 mLimit_entrades;
        public Int32 Limit_entrades
        {
            set { mLimit_entrades = value; }
            get { return mLimit_entrades; }
        }

        private Torneig Torneig;
        public Torneig mTorneig
        {
            set { Torneig = value; }
            get { return Torneig; }
        }

        public String TotalPartidasJugadasTotal
        {  
            get
            {
                String resultado = "Grup " + this.Num;
                Int32 idTorneig = this.Torneig.Id;
                Int32 partidasJugadas = TorneigBD.selectCountPartidesJugadasPerIdTorneigNumGrup(idTorneig, this.Num);
                Int32 partidasTotales = TorneigBD.selectCountPartidesTotalesPerIdTorneigNumGrup(idTorneig, this.Num);
                resultado = resultado + " Partides jugades: " + partidasJugadas + "/" + partidasTotales;

                return resultado;
            }
            set { }
        }

        public String NumDesc
        {
            get
            {
                String resultado = this.Num + "";

                resultado = resultado + ": " + Description;

                return resultado;
            }
            set { }
        }
        

    }
}
