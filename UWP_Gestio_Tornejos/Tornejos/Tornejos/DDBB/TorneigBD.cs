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

        public static float selectCoeficientDeUnInscrit(Inscrit i)
        {
            float coeficient = 0;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select coeficient_base from estadistica_modalitat inner join soci on estadistica_modalitat.soci_id = soci.id left join inscrit on inscrit.soci_id = soci.id where estadistica_modalitat.modalitat_id = 1 and soci.id = @sociId";
                    UtilsDB.AddParameter(consulta, "sociId", i.Soci.Id, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        coeficient = reader.GetFloat(reader.GetOrdinal("coeficient_base"));

                    }
                }
            }
            return coeficient;
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
                        DateTime dataAlta = new DateTime();
                        dataAlta = reader.GetDateTime(reader.GetOrdinal("data_inici"));
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

        public static ObservableCollection<Inscrit> selectInscritsDeUnTorneig(Int32 idTorneig)
        {
            DateTime data;
            ObservableCollection<Inscrit> inscrits = new ObservableCollection<Inscrit>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select inscrit.* from inscrit inner join soci on inscrit.soci_id = soci.id left join estadistica_modalitat on estadistica_modalitat.soci_id = soci.id where inscrit.torneig_id = @idTorneig and inscrit.grup_num is NULL and estadistica_modalitat.modalitat_id = 1 order by estadistica_modalitat.coeficient_base desc";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Inscrit i;

                        Int32 idS = reader.GetInt32(reader.GetOrdinal("soci_id"));
                        Int32 idT = reader.GetInt32(reader.GetOrdinal("torneig_id"));
                        Int32 numG = -1;
                        try
                        {
                            numG = reader.GetInt32(reader.GetOrdinal("grup_num"));
                        }
                        catch (Exception e)
                        {

                        }
                        data = reader.GetDateTime(reader.GetOrdinal("data"));

                        Soci s = TorneigBD.selectSociPerId(idS);
                        Torneig t = TorneigBD.selectTorneigPerId(idT);
                        if(numG != -1)
                        {
                            Grup g = TorneigBD.selectGrupDeUnTorneigIUnGrup(idT, numG);
                            i = new Inscrit(s, t, g, data);
                            inscrits.Add(i);
                        }else {
                            i = new Inscrit(s, t, null, data);
                            inscrits.Add(i);
                        }

                    }

                }
            }
            return inscrits;
        }

        public static ObservableCollection<Inscrit> selectInscritsDeUnTorneigIGrup(Int32 idTorneig, Grup g)
        {
            DateTime data;
            ObservableCollection<Inscrit> inscrits = new ObservableCollection<Inscrit>();
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select inscrit.* from inscrit where inscrit.torneig_id = @idTorneig and inscrit.grup_num = @numGrup";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", g.Num, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Inscrit i;

                        Int32 idS = reader.GetInt32(reader.GetOrdinal("soci_id"));
                        Int32 idT = reader.GetInt32(reader.GetOrdinal("torneig_id"));
                        Int32 numG = -1;
                        try
                        {
                            numG = reader.GetInt32(reader.GetOrdinal("grup_num"));
                        }
                        catch (Exception e)
                        {

                        }
                        data = reader.GetDateTime(reader.GetOrdinal("data"));

                        Soci s = TorneigBD.selectSociPerId(idS);
                        Torneig t = TorneigBD.selectTorneigPerId(idT);
                        if (numG != -1)
                        {
                            Grup gr = TorneigBD.selectGrupDeUnTorneigIUnGrup(idT, numG);
                            i = new Inscrit(s, t, gr, data);
                            inscrits.Add(i);
                        }
                        else
                        {
                            i = new Inscrit(s, t, null, data);
                            inscrits.Add(i);
                        }
                    }

                }
            }
            return inscrits;
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
            DateTime finalitzatONo;
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
                        try
                        {
                            finalitzatONo = reader.GetDateTime(reader.GetOrdinal("data_finalitzacio"));
                            if(finalitzatONo <= DateTime.Now || ( finalitzatONo.Day == DateTime.Now.Day && finalitzatONo.Month == DateTime.Now.Month && finalitzatONo.Year == DateTime.Now.Year))
                            {
                                return false;
                            }
                        }
                        catch (Exception e)
                        {
                            return true;
                        }
                    }
                }
            }
            return true;
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

        internal static void insertGrupAUnTorneig(int id, Grup g)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"INSERT INTO grup (num, description, caramboles_victoria, limit_entrades, torneig_id) VALUES (@GrupNum, @desc, @caramboles, @entrades, @idTorneig);";
                    UtilsDB.AddParameter(consulta, "GrupNum", g.Num, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "desc", g.Description, MySqlDbType.String);
                    UtilsDB.AddParameter(consulta, "caramboles", g.Caramboles_victoria, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "entrades", g.Limit_entrades, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "idTorneig", id, MySqlDbType.Int32);
                    consulta.ExecuteNonQuery();
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
                        Torneig t = TorneigBD.selectTorneigPerId(idTorneig);
                        Grup g = new Grup(num, description, carambolesVictoria, limitEntrades, t);

                        grups.Add(g);
                    }

                }
            }
            return grups;
        }

        public static Torneig selectTorneigPerId(int idTorneig)
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

        internal static Int32 selectPartidesJugadesDeUnInscrit(int idTorneig, int num, Inscrit inscrit)
        {
            Int32 contador = 0;
                using (MySqlConnection connexio = MySQL.GetConnexio())
                {
                    connexio.Open();
                    using (MySqlCommand consulta = connexio.CreateCommand())
                    {
                        consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig and grup_num = @numGrup and (inscrit_a = @idSoci or inscrit_b = @idSoci) and estat_partida = 'jugada'";
                        UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                        UtilsDB.AddParameter(consulta, "numGrup", num, MySqlDbType.Int32);
                        UtilsDB.AddParameter(consulta, "idSoci", inscrit.Soci.Id, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                    }
                }
        }

        internal static Int32 selectPartidesGuanyadesDeUnInscrit(int idTorneig, int num, Inscrit inscrit)
        {
            Int32 contador = 0;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig and grup_num = @numGrup and (inscrit_a = @idSoci and guanyador = 'A') or (inscrit_b = @idSoci and guanyador = 'B') and estat_partida = 'jugada'";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", num, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "idSoci", inscrit.Soci.Id, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        internal static Int32 selectPartidesPerdudesDeUnInscrit(int idTorneig, int num, Inscrit inscrit)
        {
            Int32 contador = 0;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig and grup_num = @numGrup and (inscrit_a = @idSoci and guanyador = 'B') or (inscrit_b = @idSoci and guanyador = 'A') and estat_partida = 'jugada'";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", num, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "idSoci", inscrit.Soci.Id, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
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


        internal static void insertTorneig(Torneig t, String data)
        {
            String b;
            if(t.PreinscripcioOberta == 0)
            {
                b = "true";
            }else
            {
                b = "false";
            }
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"INSERT INTO torneig (nom, data_inici, preinscripcio_oberta, modalitat_id) VALUES (@nomTorneig, @data, @actiu, @modalitat);";
                    UtilsDB.AddParameter(consulta, "nomTorneig", t.Nom, MySqlDbType.String);
                    UtilsDB.AddParameter(consulta, "data", data, MySqlDbType.String);
                    UtilsDB.AddParameter(consulta, "actiu", t.PreinscripcioOberta, MySqlDbType.Int32);
                    Int64 modalitatId = selectModalitatPerId((Int32)(long)t.Modalitat.Id).Id;
                    UtilsDB.AddParameter(consulta, "modalitat", modalitatId, MySqlDbType.Int32);

                    consulta.ExecuteNonQuery();
                }
            }
        }


        internal static Modalitat selectModalitatPerNom(string mSeleccionada)
        {
            Modalitat m = null;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from modalitat m where m.description = @mNom";
                    UtilsDB.AddParameter(consulta, "mNom", mSeleccionada, MySqlDbType.String);

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

        internal static int selectCountPartidesTotalesDeTorneig(int idTorneig)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select count(*) from partida where torneig_id = @idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    return ((Int32)(long)consulta.ExecuteScalar());
                }
            }
        }

        internal static int tancarTorneig(int id)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();

                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;
                    consulta.CommandText = @"update torneig set data_finalitzacio=@data_final where id = @idTorneig";

                    String dateTimeCorrecto = getDataSQLFromDateTime(DateTime.Now);

                    UtilsDB.AddParameter(consulta, "data_final", dateTimeCorrecto, MySqlDbType.String);
                    UtilsDB.AddParameter(consulta, "idTorneig", id, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                        return 1;
                    }
                    catch (Exception e)
                    {
                        return 0;
                    }
                }
            }
        }

        internal static void EsborrarTorneig(int idTorneig)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;

                    consulta.CommandText = "delete from torneig where id =@idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                    }
                    catch (Exception e)
                    {
                        trans.Rollback();
                    }
                }
            }
        }

        internal static void EsborrarInscritsDeUnTorneig(int idTorneig)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;

                    consulta.CommandText = "delete from inscrit where torneig_id =@idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                    }
                    catch (Exception e)
                    {
                        trans.Rollback();
                    }
                }
            }
        }

        internal static void EsborrarGrupsDeUnTorneig(int idTorneig)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;

                    consulta.CommandText = "delete from grup where torneig_id =@idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                    }
                    catch (Exception e)
                    {
                        trans.Rollback();
                    }
                }
            }
        }

        internal static void EsborrarPartidesDeUnTorneig(int idTorneig)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;

                    consulta.CommandText = "delete from partida where torneig_id =@idTorneig";
                    UtilsDB.AddParameter(consulta, "idTorneig", idTorneig, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();

                    }
                    catch (Exception e)
                    {
                        trans.Rollback();
                    }
                }

            }
        }

        internal static int tancarPreinscripcioTorneig(int id)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();

                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;
                    consulta.CommandText = @"update torneig set preinscripcio_oberta=0 where id = @idTorneig";

                    UtilsDB.AddParameter(consulta, "idTorneig", id, MySqlDbType.Int32);

                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                        return 1;
                    }
                    catch (Exception e)
                    {
                        return 0;
                    }
                }
            }
        }

        private static Grup selectGrupDeUnTorneigIUnGrup(int idT, int numG)
        {
            Grup g = null;
            //---------------------------------
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {

                    consulta.CommandText = @"select * from grup where torneig_id = @idTorneig and num = @numGrup";
                    UtilsDB.AddParameter(consulta, "idTorneig", idT, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "numGrup", numG, MySqlDbType.Int32);
                    MySqlDataReader reader = consulta.ExecuteReader();

                    while (reader.Read())
                    {
                        Int32 num = reader.GetInt32(reader.GetOrdinal("num"));
                        string description = reader.GetString(reader.GetOrdinal("description"));
                        Int32 carambolesVictoria = reader.GetInt32(reader.GetOrdinal("caramboles_victoria"));
                        Int32 limitEntrades = reader.GetInt32(reader.GetOrdinal("limit_entrades"));
                        Torneig t = TorneigBD.selectTorneigPerId(idT);
                        g = new Grup(num, description, carambolesVictoria, limitEntrades, t);

                    }
                }
            }
            return g;
        }

        private static Soci selectSociPerId(int idS)
        {
            Soci s = null;
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.CommandText = @"select * from soci s where s.Id = @sId";
                    UtilsDB.AddParameter(consulta, "sId", idS, MySqlDbType.Int32);

                    MySqlDataReader reader = consulta.ExecuteReader();
                    while (reader.Read())
                    {
                        Int32 id = reader.GetInt32(reader.GetOrdinal("id"));
                        string nif = reader.GetString(reader.GetOrdinal("nif"));
                        string nom = reader.GetString(reader.GetOrdinal("nom"));
                        string cognom1 = reader.GetString(reader.GetOrdinal("cognom1"));
                        string cognom2 = reader.GetString(reader.GetOrdinal("cognom2"));
                        DateTime dataAlta = reader.GetDateTime(reader.GetOrdinal("data_alta"));
                        string passwordHash = reader.GetString(reader.GetOrdinal("password_hash"));
                        Int32 actiu = reader.GetInt32(reader.GetOrdinal("actiu"));
                        s = new Soci(id, nif, nom, cognom1, cognom2, dataAlta, passwordHash, actiu);
                    }

                }
            }
            return s;
        }

        internal static void updateInscritEnUnGrup(Inscrit i, Grup g)
        {
            using (MySqlConnection connexio = MySQL.GetConnexio())
            {
                connexio.Open();

                MySqlTransaction trans = connexio.BeginTransaction();
                using (MySqlCommand consulta = connexio.CreateCommand())
                {
                    consulta.Transaction = trans;
                    consulta.CommandText = @"update inscrit set grup_num = @grupNum where soci_id = @idSoci and torneig_id = @idTorneig";

                    UtilsDB.AddParameter(consulta, "grupNum", g.Num, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "idTorneig", i.Torneig.Id, MySqlDbType.Int32);
                    UtilsDB.AddParameter(consulta, "idSoci", i.Soci.Id, MySqlDbType.Int32);
                    try
                    {
                        consulta.ExecuteNonQuery();
                        trans.Commit();
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }


        public static String getDataSQLFromDateTime(DateTime data)
        {
            return data.Year + "-" + data.Month + "-" + data.Day;
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
