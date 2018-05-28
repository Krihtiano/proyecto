using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tornejos.Model
{
    class Partida
    {
        public Partida(Int32 mId, Int32 mCarambolesA, Int32 mCarambolesB, Int32 mNumEntrades, Torneig mTorneig, Grup mGrup, Inscrit mInscritA, Inscrit mInscritB, String mMotiuVictoria, Char mGuanyador, String mEstatPartida)
        {

            this.Id = mId;
            this.CarambolesA = mCarambolesA;
            this.CarambolesB = mCarambolesB;
            this.NumEntrades = mNumEntrades;
            this.Torneig = mTorneig;
            this.Grup = mGrup;
            this.InscritA = mInscritA;
            this.InscritB = mInscritB;
            this.MotiuVictoria = mMotiuVictoria;
            this.Guanyador = mGuanyador;
            this.EstatPartida = mEstatPartida;

        }

        private Int32 mId;
        public Int32 Id
        {
            set { mId = value; }
            get { return mId; }
        }

        private Int32 mCarambolesA;
        public Int32 CarambolesA
        {
            set { mCarambolesA = value; }
            get { return mCarambolesA; }
        }

        private Int32 mCarambolesB;
        public Int32 CarambolesB
        {
            set { mCarambolesB = value; }
            get { return mCarambolesB; }
        }

        private DateTime mData;
        public DateTime Data
        {
            set { mData = value; }
            get { return mData; }
        }

        private Int32 mNumEntrades;
        public Int32 NumEntrades
        {
            set { mNumEntrades = value; }
            get { return mNumEntrades; }
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

        private Inscrit mInscritA;
        public Inscrit InscritA
        {
            set { mInscritA = value; }
            get { return mInscritA; }
        }

        private Inscrit mInscritB;
        public Inscrit InscritB
        {
            set { mInscritB = value; }
            get { return mInscritB; }
        }

        private String mMotiuVictoria;
        public String MotiuVictoria
        {
            set { mMotiuVictoria = value; }
            get { return mMotiuVictoria; }
        }

        private Char mGuanyador;
        public Char Guanyador
        {
            set { mGuanyador = value; }
            get { return mGuanyador; }
        }

        private String mEstatPartida;
        public String EstatPartida
        {
            set { mEstatPartida = value; }
            get { return mEstatPartida; }
        }

        public String AvsB
        {
            get
            {
                try
                {
                    String resultado = mInscritA.NomCognoms + " vs " + mInscritB.NomCognoms;
                    return resultado;
                }
                catch (Exception ex)
                {

                }
                return null;
            }
            set { }
        }


    }
}
