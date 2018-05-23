using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tornejos.Model
{
    class Soci
    {
        /*id int(11) NOT NULL AUTO_INCREMENT,
          nif varchar(9) NOT NULL,
          nom varchar(30) NOT NULL,
          cognom1 varchar(30) NOT NULL,
          cognom2 varchar(30) NOT NULL,
          data_alta timestamp NOT NULL,
          password_hash varchar(32) NOT NULL,
          foto longblob,
          actiu int(1) NOT NULL, */

        public Soci(Int32 mId, String mNif, String mNom, String mCognom, String mCognom2, DateTime mDataAlta, String mPasswordHash, byte [] mFoto, Int32 mActiu)
        {
            this.Id = mId;
            this.Nif = mNif;
            this.Nom = mNom;
            this.Cognom = mCognom;
            this.Cognom2 = mCognom2;
            this.DataAlta = mDataAlta;
            this.PasswordHash = mPasswordHash;
            this.Foto = mFoto;
            this.Actiu = mActiu;
        }

        public Soci(Int32 mId, String mNif, String mNom, String mCognom, String mCognom2, DateTime mDataAlta, String mPasswordHash, Int32 mActiu)
        {
            this.Id = mId;
            this.Nif = mNif;
            this.Nom = mNom;
            this.Cognom = mCognom;
            this.Cognom2 = mCognom2;
            this.DataAlta = mDataAlta;
            this.PasswordHash = mPasswordHash;
            this.Actiu = mActiu;
        }

        private Int32 mId;
        public Int32 Id
        {
            set { mId = value; }
            get { return mId; }
        }

        private string mNif;
        public string Nif
        {
            get { return mNif; }
            set { mNif = value; }
        }

        private string mNom;
        public string Nom
        {
            get { return mNom; }
            set { mNom = value; }
        }

        private string mCognom;
        public string Cognom
        {
            get { return mCognom; }
            set { mCognom = value; }
        }

        private string mCognom2;
        public string Cognom2
        {
            get { return mCognom2; }
            set { mCognom2 = value; }
        }

        private DateTime mDataAlta;
        public DateTime DataAlta
        {
            get { return mDataAlta; }
            set { mDataAlta = value; }
        }

        private string mPasswordHash;
        public string PasswordHash
        {
            get { return mPasswordHash; }
            set { mPasswordHash = value; }
        }

        private byte [] mFoto;
        public byte[] Foto
        {
            get { return mFoto; }
            set { mFoto = value; }
        }

        private Int32 mActiu;
        public Int32 Actiu
        {
            set { mActiu = value; }
            get { return mActiu; }
        }


    }
}
