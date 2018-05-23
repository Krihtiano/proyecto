using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tornejos.Model;

namespace Tornejos.DDBB
{
    class TorneigBD
    {
        public static ObservableCollection<Modalitat> selectModalitats()
        {
            ObservableCollection<Modalitat> modalitats = new ObservableCollection<Modalitat>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from modalitat";

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        string descripcio = reader.GetString(reader.GetOrdinal("description"));
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        Modalitat m = new Modalitat(id, descripcio);

                        modalitats.Add(m);
                    }

                }
            }
            return modalitats;
        }

        public static Modalitat selectModalitatPerId(Int32 mId)
        {
            Modalitat m = null;


            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    

                    consulta.CommandText = @"select * from modalitat m where m.Id = @mId";
                    UtilsDB.AddParameter(consulta, "mId", mId, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        string descripcio = reader.GetString(reader.GetOrdinal("description"));
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        m = new Modalitat(id, descripcio);

                    }

                }
            }
            return m;
        }

        public static ObservableCollection<Torneig> selectTornejos()
        {
            ObservableCollection<Torneig> tornejos = new ObservableCollection<Torneig>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from torneig";

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        string nom = reader.GetString(reader.GetOrdinal("nom"));
                        DateTime dataAlta = reader.GetDateTime(reader.GetOrdinal("data_inici"));
                        DateTime dataFinalitzacio = new DateTime();
                        try
                        {
                            dataFinalitzacio = reader.GetDateTime(reader.GetOrdinal("data_finalitzacio"));
                        }
                        catch (Exception e)
                        {

                        }
                         
                        Int32 preinscripcioOberta = reader.GetInt32(reader.GetOrdinal("preinscripcio_oberta"));
                        Modalitat mod = TorneigBD.selectModalitatPerId(reader.GetInt32(reader.GetOrdinal("modalitat_id")));
                        Torneig t = new Torneig(id, nom, dataAlta, dataFinalitzacio, preinscripcioOberta, mod);

                        tornejos.Add(t);
                    }

                }
            }
            return tornejos;
        }

        internal static Int32 selectTotalPartidesPerTorneig(int id)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", id, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        internal static Int32 selectTotalGrupsPerTorneig(int id)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from grup where torneig_id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", id, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        internal static bool TorneigActiuONo(int idTorneig)
        {
            Boolean preinscripcioOberta = false;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from torneig where id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        preinscripcioOberta = reader.GetBoolean(reader.GetOrdinal("preinscripcio_oberta"));
                    }
                }
            }
            return preinscripcioOberta;
        }

        internal static int selectCountPartidesTotalesPerIdTorneigNumGrup(int idTorneig, int numGrup)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig and grup_num = @numGrup";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", numGrup, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        internal static int selectCountPartidesJugadasPerIdTorneigNumGrup(int idTorneig, int numGrup)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig and grup_num = @numGrup and estat_partida = 'jugada'";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", numGrup, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        public static ObservableCollection<Grup> selectGrupsDeUnTorneig(Int32 idTorneig)
        {
            ObservableCollection<Grup> grups = new ObservableCollection<Grup>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from grup where torneig_id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    MySqlDataReader reader = consulta.ExecuteReader();

                    while (reader.Read())
                    {
                        Int32 num = reader.GetInt32(reader.GetOrdinal("num"));
                        string description = reader.GetString(reader.GetOrdinal("description"));
                        Int32 carambolesVictoria = reader.GetInt32(reader.GetOrdinal("caramboles_victoria"));
                        Int32 limitEntrades = reader.GetInt32(reader.GetOrdinal("limit_entrades"));
                        Torneig t = TorneigBD.selectToreigPerId(idTorneig);
                        Grup g = new Grup(num, description, carambolesVictoria, limitEntrades, t);

                        grups.Add(g);
                    }

                }
            }
            return grups;
        }

        private static Torneig selectToreigPerId(int idTorneig)
        {
            Torneig t = null;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from torneig where id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    MySqlDataReader reader = consulta.ExecuteReader();

                    while (reader.Read())
                    {
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        string nom = reader.GetString(reader.GetOrdinal("nom"));
                        DateTime dataAlta = reader.GetDateTime(reader.GetOrdinal("data_inici"));
                        DateTime dataFinalitzacio = new DateTime();
                        try
                        {
                            dataFinalitzacio = reader.GetDateTime(reader.GetOrdinal("data_finalitzacio"));
                        }
                        catch (Exception e)
                        {

                        }

                        Int32 preinscripcioOberta = reader.GetInt32(reader.GetOrdinal("preinscripcio_oberta"));
                        Modalitat mod = TorneigBD.selectModalitatPerId(reader.GetInt32(reader.GetOrdinal("modalitat_id")));
                        t = new Torneig(id, nom, dataAlta, dataFinalitzacio, preinscripcioOberta, mod);

                    }

                }
            }
            return t;
        }

        internal static object selectTornejosFiltrados(bool data, bool estat)
        {
            String dataString = data == true ? "asc" : "desc";
            Int32 estatInt = estat == true ? 1 : 0;

            ObservableCollection<Torneig> tornejos = new ObservableCollection<Torneig>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = data == true ? @"select * from torneig where preinscripcio_oberta = @estat order by data_inici asc" : @"select * from torneig where preinscripcio_oberta = @estat order by data_inici desc";
                    UtilsDB.AddParameter(consulta, "estat", estatInt, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        string nom = reader.GetString(reader.GetOrdinal("nom"));
                        DateTime dataAlta = reader.GetDateTime(reader.GetOrdinal("data_inici"));
                        DateTime dataFinalitzacio = new DateTime();
                        try
                        {
                            dataFinalitzacio = reader.GetDateTime(reader.GetOrdinal("data_finalitzacio"));
                        }
                        catch (Exception e)
                        {

                        }
                        Int32 preinscripcioOberta = reader.GetInt32(reader.GetOrdinal("preinscripcio_oberta"));
                        Modalitat mod = TorneigBD.selectModalitatPerId(reader.GetInt32(reader.GetOrdinal("modalitat_id")));
                        Torneig t = new Torneig(id, nom, dataAlta, dataFinalitzacio, preinscripcioOberta, mod);

                        tornejos.Add(t);
                    }

                }
            }
            return tornejos;
        }

        internal static object selectTornejosFiltrados(bool data)
        {
            ObservableCollection<Torneig> tornejos = new ObservableCollection<Torneig>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = data == true ? @"select * from torneig order by data_inici asc" : @"select * from torneig order by data_inici desc";

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        string nom = reader.GetString(reader.GetOrdinal("nom"));
                        DateTime dataAlta = reader.GetDateTime(reader.GetOrdinal("data_inici"));
                        DateTime dataFinalitzacio = new DateTime();
                        try
                        {
                            dataFinalitzacio = reader.GetDateTime(reader.GetOrdinal("data_finalitzacio"));
                        }
                        catch (Exception e)
                        {

                        }
                        Int32 preinscripcioOberta = reader.GetInt32(reader.GetOrdinal("preinscripcio_oberta"));
                        Modalitat mod = TorneigBD.selectModalitatPerId(reader.GetInt32(reader.GetOrdinal("modalitat_id")));
                        Torneig t = new Torneig(id, nom, dataAlta, dataFinalitzacio, preinscripcioOberta, mod);

                        tornejos.Add(t);
                    }

                }
            }
            return tornejos;
        }
        /*public static ObservableCollection<Modalitat> GetAllDept(string numeroDept = "" , string nomLocalitat = "")
        {
            ObservableCollection<Dept> depts = new ObservableCollection<Dept>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            { 
                connexio.Open();
                    // crear la comanda SQL
                    using(MySqlCommand consulta = connexio.CreateCommand())
                    {

                        int numDeptInteger;
                        bool conversioCorrecta= Int32.TryParse(numeroDept, out numDeptInteger);
                        if (!conversioCorrecta) numDeptInteger = -1;

                        consulta.CommandText = @"select * from dept where (@p_numeroDept=-1 or dept_no = @p_numeroDept) and (@p_nomLocalitat='' or loc like @p_nomLocalitat) ";

                        UtilsDB.AddParameter(consulta, "p_numeroDept", numDeptInteger, MySqlDbType.Int32);
                        UtilsDB.AddParameter(consulta, "p_nomLocalitat",  nomLocalitat+"%", MySqlDbType.String);

                        MySqlDataReader reader = consulta.ExecuteReader();
                        while(reader.Read())
                        {

                            Dictionary<string, object> fila = UtilsDB.getFila(reader);
                            Dept d = new Dept( (int)(fila["DEPT_NO"]), (string)fila["DNOM"], (string)fila["LOC"]);
                            
                            depts.Add(d);
                        }
                    
                    }                    
                }
                return depts;
            }
            
        

        internal static bool updateOrInsertDept(Dept d, bool esInsert)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();

                    MySqlTransaction trans = connexio.BeginTransaction();
                    // crear la comanda SQL
                    using (MySqlCommand consulta = connexio.CreateCommand())
                    {
                        consulta.Transaction = trans;

                        if (esInsert)
                        {
                            consulta.CommandText = @"insert into dept (dnom, loc) values 
                                                    ( @p_dnom , @p_loc ) ";
                        }
                        else
                        {
                            consulta.CommandText = @"update dept set dnom=@p_dnom , loc=@p_loc  
                                                 where dept_no =@p_numeroDept";
                        }
                        UtilsDB.AddParameter(consulta, "p_dnom",     d.Nom, MySqlDbType.String);
                        UtilsDB.AddParameter(consulta, "p_loc",     d.Localitat, MySqlDbType.String);
                        UtilsDB.AddParameter(consulta, "p_numeroDept", d.Numero, MySqlDbType.Int32);

                        try
                        {
                            int numRows = consulta.ExecuteNonQuery();
                            if (numRows != 1)
                            {
                                trans.Rollback();

                            }
                            else
                            {
                                if(esInsert)
                                {
                                    consulta.CommandText = "SELECT LAST_INSERT_ID()";
                                    d.Numero = (int)(Int64)consulta.ExecuteScalar();
                                }

                                trans.Commit();
                                return true;
                            }

                        }
                        catch (Exception e)
                        {
                            trans.Rollback();
                        }

                        return false;

                    }
                
            }
        }

        internal static long CountEmp(int numero)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                    // crear la comanda SQL
                    using (MySqlCommand consulta = connexio.CreateCommand())
                    {


                        consulta.CommandText = @"select count(*) from emp  
                                                    where   dept_no = @p_numeroDept";


                        UtilsDB.AddParameter(consulta, "p_numeroDept", numero, MySqlDbType.Int32);


                        return ((long)consulta.ExecuteScalar());
                    }
                }
            
        }

        internal static bool RemoveDept(int numeroDept)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();

                    MySqlTransaction trans = connexio.BeginTransaction();
                    // crear la comanda SQL
                    using (MySqlCommand consulta = connexio.CreateCommand())
                    {
                        consulta.Transaction = trans;

                        consulta.CommandText = "delete from dept where dept_no =@p_numeroDept";
                        UtilsDB.AddParameter(consulta, "p_numeroDept", numeroDept, MySqlDbType.Int32);

                        try
                        {
                            int numRows = consulta.ExecuteNonQuery();
                            if (numRows > 1)
                            {
                                trans.Rollback();

                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }

                        }catch(Exception e)
                        {
                            trans.Rollback();
                        }

                        return false;

                    }
                }
          
        } */
    }
}
