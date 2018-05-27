using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tornejos.DDBB;

namespace Tornejos.Model
{
    public class Torneig
    {

        public Torneig(Int32 mId, String mNom, DateTime mDataInici, DateTime mDataFinalitzacio, Int32 mPreinscripcioOberta, Modalitat mModalitat, Int32 mGrupsCreats)
        {
            this.Id = mId;
            this.Nom = mNom;
            this.DataInici = mDataInici;
            this.DataFinalitzacio = mDataFinalitzacio;
            this.PreinscripcioOberta = mPreinscripcioOberta;
            this.Modalitat = mModalitat;
            this.GrupsCreats = mGrupsCreats;
        }

        public Torneig(Int32 mId, String mNom, DateTime mDataInici, Int32 mPreinscripcioOberta, Modalitat mModalitat, Int32 mGrupsCreats)
        {
            this.Id = mId;
            this.Nom = mNom;
            this.DataInici = DataInici;
            this.PreinscripcioOberta = mPreinscripcioOberta;
            this.Modalitat = mModalitat;
            this.GrupsCreats = mGrupsCreats;
        }

        public Torneig( String mNom, DateTime mDataInici, Int32 mPreinscripcioOberta, Modalitat mModalitat, Int32 mGrupsCreats)
        {
            this.Nom = mNom;
            this.DataInici = DataInici;
            this.PreinscripcioOberta = mPreinscripcioOberta;
            this.Modalitat = mModalitat;
            this.GrupsCreats = mGrupsCreats;
        }

        private int mId;
        public int Id
        {
            set { mId = value; }
            get { return mId; }
        }

        private int mGrupsCreats;
        public int GrupsCreats
        {
            set { mGrupsCreats = value; }
            get { return mGrupsCreats; }
        }

        private string mNom;
        public string Nom
        {
            get { return mNom; }
            set
            {
                mNom = value;
            }
        }

        private DateTime mDataInici;
        public DateTime DataInici
        {
            get { return mDataInici; }
            set
            {
                mDataInici = value;
            }
        }

        private DateTime mDataFinalitzacio;
        public DateTime DataFinalitzacio
        {
            get { return mDataFinalitzacio; }
            set
            {
                mDataFinalitzacio = value;
            }
        }

        private int mPreinscripcioOberta;
        public int PreinscripcioOberta
        {
            get { return mPreinscripcioOberta; }
            set
            {
                mPreinscripcioOberta = value;
            }
        }

        private Modalitat mModalitat;
        public Modalitat Modalitat
        {
            get { return mModalitat; }
            set
            {
                mModalitat = value;
            }
        }

        public string Informacio
        {
            get {
                String respuesta = "";
                if (TorneigBD.TorneigActiuONo(this.Id))
                {
                    respuesta = respuesta + "Data Inici: " + DataInici.Year + "-" + DataInici.Month + "-" + DataInici.Day + TotalPartides + TotalGrups;
                }else
                {
                    respuesta = respuesta + "Data finalització: " + DataFinalitzacio.Year + "-" + DataFinalitzacio.Month + "-" + DataFinalitzacio.Day;
                }

                return respuesta;
            }
            set { }
        }

        public String TotalPartides
        {
            get {
                Int32 totalPartides = TorneigBD.selectTotalPartidesPerTorneig(this.Id);
                return " Partides: " + totalPartides; 
                }
            set { }
        }
        public String TotalGrups
        {
            get
            {
                Int32 totalGrups = TorneigBD.selectTotalGrupsPerTorneig(this.Id);
                return " Grups: " + totalGrups;
            }
            set { }
        }

    }
}
