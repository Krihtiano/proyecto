using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
 
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tornejos.Model
{
    class MySQL
    {

        public static MySqlConnection GetConnexio()
        {
            //-----------------------------------------------
            // registrem provider d'encoding....necessari!
            //-----------------------------------------------
            System.Text.EncodingProvider ppp;
            ppp = System.Text.CodePagesEncodingProvider.Instance;
            Encoding.RegisterProvider(ppp);
            //-----------------------------------------------

            string conString = "server = 92.222.27.83; uid = m2-clopez; pwd = 47751177A;charset=utf8; SslMode=None ; database = m2_clopez";
            return new MySqlConnection(conString);
        }
         
    }
}
