using _20171122_SQLite.model;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _20171122_SQLite.db
{
    class TorneigDB
    {

        public static ObservableCollection<Dept> GetAllDept( 
            string numeroDept = "" , 
            string nomLocalitat = "")
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

                        consulta.CommandText = @"select * from dept  
                                                    where (@p_numeroDept=-1 or dept_no = @p_numeroDept) and
                                                          (@p_nomLocalitat='' or loc like @p_nomLocalitat) ";


                        UtilsDB.AddParameter(consulta, "p_numeroDept",      numDeptInteger, MySqlDbType.Int32);
                        UtilsDB.AddParameter(consulta, "p_nomLocalitat",  nomLocalitat+"%", MySqlDbType.String);




                        /*
                        if (numeroDept!=null)
                        {
                            consulta.CommandText += " where dept_no = @numDept";

                            UtilsDB.AddParameter(consulta, "numDept", numeroDept, DbType.Int32);
                            
                        }*/
                        MySqlDataReader reader = consulta.ExecuteReader();
                        while(reader.Read())
                        {
                            //int numero =        reader.GetInt32( reader.GetOrdinal("DEPT_NO"));
                            //string  nom =       reader.GetString(reader.GetOrdinal("DNOM"));
                            //string localitat =  reader.GetString(reader.GetOrdinal("LOC"));
                            //Dept d = new Dept(numero, nom, localitat);

                            Dictionary<string, object> fila = UtilsDB.getFila(reader);
                            Dept d = new Dept( (int)(fila["DEPT_NO"]), (string)fila["DNOM"], (string)fila["LOC"]);

                            
                            depts.Add(d);
                        }
                    
                    }                    
                }
                return depts;
            }
            
             //---------------------------------
            
        

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
            
        }
    }
}
