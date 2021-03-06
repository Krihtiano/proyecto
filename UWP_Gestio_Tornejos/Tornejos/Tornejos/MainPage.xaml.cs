﻿using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Tornejos.DDBB;
using Tornejos.Model;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

namespace Tornejos
{
    public sealed partial class MainPage : Page
    {
        private Boolean data = false, estat = false;



        public MainPage()
        {
            this.InitializeComponent();
            inflarCmbModalitats();
            inflarLvTornejos();
        }

        private void inflarLvTornejos()
        {
            lvTornejos.ItemsSource = TorneigBD.selectTornejos();
        }

        private void inflarCmbModalitats()
        {
            ObservableCollection<Modalitat> modalitats = TorneigBD.selectModalitats();
            for (int i = 0; i < modalitats.Count; i++)
            {
                cmbModalitats.Items.Add(modalitats[i].Description);
            }
        }

        private void lvTornejos_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Torneig t = (Torneig)lvTornejos.SelectedItem;
            if (t == null)
            {
                lvGrups.ItemsSource = null;
                lvGrupsDisponibles.ItemsSource = null;
                lvInscrits.ItemsSource = null;
            }
            else
            {
                if(t.DataFinalitzacio == null || t.DataFinalitzacio.Year == 0001) 
                {
                    ponerCamposEnabledDisabled(true);
                    lvGrups.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
                    lvGrupsDisponibles.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
                    lvInscrits.ItemsSource = TorneigBD.selectInscritsDeUnTorneig(t.Id);
                    return;
                }

                if (t.DataFinalitzacio <= DateTime.Now || (t.DataFinalitzacio.Day == DateTime.Now.Day && t.DataFinalitzacio.Month == DateTime.Now.Month && t.DataFinalitzacio.Year == DateTime.Now.Year))
                {
                    ponerCamposEnabledDisabled(false);
                }else
                {
                    ponerCamposEnabledDisabled(true);
                }

                lvGrups.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
                lvGrupsDisponibles.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
                lvInscrits.ItemsSource = TorneigBD.selectInscritsDeUnTorneig(t.Id);
            }
        }

        private void ponerCamposEnabledDisabled(bool b)
        {
            
            lvInscrits.IsEnabled = b;
            lvGrupsDisponibles.IsEnabled = b;
            txtCarambolesGrup.IsEnabled = b;
            txtLimitEntradesGrup.IsEnabled = b;
            txtNomGrup.IsEnabled = b;
            btnCrearGrup.IsEnabled = b;

        }

        private void btnFiltreEstat_Click(object sender, RoutedEventArgs e)
        {
            if(estat == false)
            {
                lvTornejos.ItemsSource = TorneigBD.selectTornejosFiltrados(data, estat);
                btnFiltreEstat.Content = "Estat Tancat";
                estat = true;
            }
            else
            {
                lvTornejos.ItemsSource = TorneigBD.selectTornejosFiltrados(data, estat);
                btnFiltreEstat.Content = "Estat Obert";
                estat = false;
            }
        }

        private void btnFiltreData_Click(object sender, RoutedEventArgs e)
        {

            if (data == false)
            {
                lvTornejos.ItemsSource = TorneigBD.selectTornejosFiltrados(data);
                btnFiltreData.Content = "Data Descendent";
                data = true;
            }
            else
            {
                lvTornejos.ItemsSource = TorneigBD.selectTornejosFiltrados(data);
                btnFiltreData.Content = "Data Ascendent";
                data = false;
            }
        }

        private void btnCrearTorneig_Click(object sender, RoutedEventArgs e)
        {
            if(!(txbTitol.Text.Length >= 2 || txbTitol.Text.Length > 30)) {
                DisplayError("Error", "El nom del torneig es incorrecte(2-30 caracters)");
                return;
            }

            if(cmbModalitats.SelectedItem == null)
            {
                DisplayError("Error", "Modalitat incorrecte");
                return;
            }
            String mSeleccionada = cmbModalitats.SelectedItem.ToString();
            Modalitat m = TorneigBD.selectModalitatPerNom(mSeleccionada);
            DateTimeOffset sourceTime = dtpDataTorneig.Date;
            DateTime data = sourceTime.DateTime;
            String dataEnString = getDataSQLFromDateTime(data);
            Torneig t = new Torneig(txbTitol.Text, data, 1, m);
            TorneigBD.insertTorneig(t, dataEnString);
            lvTornejos.ItemsSource = TorneigBD.selectTornejos();
        }
        private void btnEliminarFiltres_Click(object sender, RoutedEventArgs e)
        {
            lvTornejos.ItemsSource = TorneigBD.selectTornejos();
            btnFiltreData.Content = "Data Ascendent";
            btnFiltreEstat.Content = "Estat Obert";
        }

        private void btnClassificacio_Click(object sender, RoutedEventArgs e)
        {
            lvClassificacioGrups.Items.Clear();
            Button b = (Button)sender;
            int index = (int)b.Tag;
            Pivote.SelectedItem =Classificacio;
            ObservableCollection<Grup> grups = TorneigBD.selectGrupsDeUnTorneig(index);
            for(int i = 0; i < grups.Count; i++)
            {
                ListView lv = new ListView();
                Grup g = grups[i];
                String grupIdNom = (g.Num + 1) + " " + g.Description;
                Grid grid = generarTablaClassificacio(g, index);

                lvClassificacioGrups.Items.Add(grupIdNom);
                lvClassificacioGrups.Items.Add(grid);


            }


        }

        private void btnEntradaResultats_Click(object sender, RoutedEventArgs e)
        {
            Button b = (Button)sender;
            int index = (int)b.Tag;
            Pivote.SelectedItem = Resultats;




        }

        private void btnTancar_Click(object sender, RoutedEventArgs e)
        {
            Torneig t = (Torneig)lvTornejos.SelectedItem;

            if (t != null)
            {
                if (t.DataFinalitzacio >= DateTime.Now || (t.DataFinalitzacio.Day == DateTime.Now.Day && t.DataFinalitzacio.Month == DateTime.Now.Month && t.DataFinalitzacio.Year == DateTime.Now.Year))
                {
                    DisplayError("Error", "Aquest torneig ja està tancat");
                    return;
                }

                Torneig g = (Torneig)lvTornejos.SelectedItem;
                int partidesDelTorneig = TorneigBD.selectCountPartidesTotalesDeTorneig(g.Id);
                if(partidesDelTorneig == 0)
                {
                    TorneigBD.tancarPreinscripcioTorneig(g.Id);
                    int seHaCerrado = TorneigBD.tancarTorneig(g.Id);
                    if(seHaCerrado == 1)
                    {
                        DisplayError("Torneig tancat", "S'ha tancat el torneig correctament");
                        lvTornejos.ItemsSource = TorneigBD.selectTornejos();
                    }else
                    {
                        DisplayError("Error", "No s'ha pogut tancar el torneig");
                    }
                }else
                {
                    DisplayError("Error", "El torneig te partides encara per finaltizar");
                }
            }
        }

        public static String getDataSQLFromDateTime(DateTime data)
        {
            return data.Year + "-" + data.Month + "-" + data.Day;
        }

        private void btnEsborrar_Click(object sender, RoutedEventArgs e)
        {
            Torneig t = (Torneig)lvTornejos.SelectedItem;
            if (t != null)
            {
                int numPartidesPerTorneig = TorneigBD.selectCountPartidesTotalesDeTorneig(t.Id);
                if (numPartidesPerTorneig >= 1)
                {
                    DisplayDeleteTorneigDialog(t.Id);
                }
                else
                {
                    TorneigBD.EsborrarPartidesDeUnTorneig(t.Id);
                    TorneigBD.EsborrarInscritsDeUnTorneig(t.Id);
                    TorneigBD.EsborrarGrupsDeUnTorneig(t.Id);
                    TorneigBD.EsborrarTorneig(t.Id);
                }
            }
        }

        private void lvInscrits_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private void btnCrearGrup_Click(object sender, RoutedEventArgs e)
        {
            Torneig t = (Torneig)lvTornejos.SelectedItem;

            if (t != null)
            {
                Int32 caramboles, entrades;

                if (!(txtNomGrup.Text.Length >= 2 || txbTitol.Text.Length > 30))
                {
                    DisplayError("Error", "El nom del grup es incorrecte (2-30 caracters)");
                    return;
                }

                try
                {
                    entrades = Int32.Parse(txtLimitEntradesGrup.Text);
                }
                catch (Exception ex)
                {
                    DisplayError("Error", "Número d'entrades incorrecte");
                    return;
                }

                try
                {
                    caramboles = Int32.Parse(txtCarambolesGrup.Text);
                }
                catch (Exception ex)
                {
                    DisplayError("Error", "Número de caramboles incorrecte");
                    return;
                }

                Int32 contadorGrups = TorneigBD.selectTotalGrupsPerTorneig(t.Id);
                Torneig tor = TorneigBD.selectTorneigPerId(t.Id);
                Grup g = new Grup(contadorGrups, txtNomGrup.Text, caramboles, entrades, tor);
                TorneigBD.insertGrupAUnTorneig(t.Id, g);
                lvGrupsDisponibles.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
            }
        }

        private void btnAfegirInscritAGrup_Click(object sender, RoutedEventArgs e)
        {
            Torneig t = (Torneig)lvTornejos.SelectedItem;
            if (lvGrupsDisponibles.SelectedItem == null || lvInscrits.SelectedItem == null)
            {
                DisplayError("Error", "Selecciona un inscrit i un grup");
            }else
            {
                Grup g = (Grup)lvGrupsDisponibles.SelectedItem;
                Inscrit i = (Inscrit)lvInscrits.SelectedItem;
                TorneigBD.updateInscritEnUnGrup(i, g);
                lvInscrits.ItemsSource = TorneigBD.selectInscritsDeUnTorneig(t.Id);
            }
        }

        private async void DisplayDeleteTorneigDialog(Int32 idTorneig)
        {
            ContentDialog TorneigDeleteDialog = new ContentDialog
            {
                Title = "Warning",
                Content = "El torneig que vols esborrar conté partides pendents, segur que vols esborrar-ho?",
                PrimaryButtonText = "Delete",
                SecondaryButtonText = "Cancel"
            };

            ContentDialogResult result = await TorneigDeleteDialog.ShowAsync();

            if (result == ContentDialogResult.Primary)
            {
                TorneigBD.EsborrarPartidesDeUnTorneig(idTorneig);
                TorneigBD.EsborrarInscritsDeUnTorneig(idTorneig);
                TorneigBD.EsborrarGrupsDeUnTorneig(idTorneig);
                TorneigBD.EsborrarTorneig(idTorneig);
                lvTornejos.ItemsSource = TorneigBD.selectTornejos();
            }
        }

        private async void DisplayError(String title, String content)
        {
            ContentDialog noWifiDialog = new ContentDialog
            {
                Title = title,
                Content = content,
                PrimaryButtonText = "Ok"
            };

            ContentDialogResult result = await noWifiDialog.ShowAsync();
        }

        public Grid generarTablaClassificacio(Grup g, Int32 idTorneig)
        {
            ObservableCollection<Inscrit> inscrits = TorneigBD.selectInscritsDeUnTorneigIGrup(idTorneig, g);

            Grid grid = new Grid();
            grid.Width = 650;
            grid.Height = 50 + (inscrits.Count * 50);

            ColumnDefinition posicio = new ColumnDefinition();
            ColumnDefinition nomJugador = new ColumnDefinition();
            ColumnDefinition partidesJugades = new ColumnDefinition();
            ColumnDefinition partidesGuanyades = new ColumnDefinition();
            ColumnDefinition partidesPerdudes = new ColumnDefinition();
            ColumnDefinition coeficient = new ColumnDefinition();
            RowDefinition rd = new RowDefinition();
            grid.RowDefinitions.Add(rd);

            posicio.Width = new GridLength(0, GridUnitType.Auto);
            nomJugador.Width = new GridLength(0, GridUnitType.Auto);
            partidesJugades.Width = new GridLength(0, GridUnitType.Auto);
            partidesGuanyades.Width = new GridLength(0, GridUnitType.Auto);
            partidesPerdudes.Width = new GridLength(0, GridUnitType.Auto);
            coeficient.Width = new GridLength(0, GridUnitType.Auto);

            grid.ColumnDefinitions.Add(posicio);
            grid.ColumnDefinitions.Add(nomJugador);
            grid.ColumnDefinitions.Add(partidesJugades);
            grid.ColumnDefinitions.Add(partidesGuanyades);
            grid.ColumnDefinitions.Add(partidesPerdudes);
            grid.ColumnDefinitions.Add(coeficient);

            TextBox tPosicio = new TextBox();
            tPosicio.IsEnabled = false;
            tPosicio.Text = "Posicio";

            TextBox tNomJugador = new TextBox();
            tNomJugador.IsEnabled = false;
            tNomJugador.Text = "Nom jugador";

            TextBox tPartidesJugades = new TextBox();
            tPartidesJugades.IsEnabled = false;
            tPartidesJugades.Text = "Partides jug.";

            TextBox tPartidesGuanyades = new TextBox();
            tPartidesGuanyades.IsEnabled = false;
            tPartidesGuanyades.Text = "Partides guanyades";

            TextBox tPartidesPerdudes = new TextBox();
            tPartidesPerdudes.IsEnabled = false;
            tPartidesPerdudes.Text = "Partides perdudes";

            TextBox tCoeficient = new TextBox();
            tCoeficient.IsEnabled = false;
            tCoeficient.Text = "Coeficient";

            grid.Children.Add(tPosicio);
            grid.Children.Add(tNomJugador);
            grid.Children.Add(tPartidesJugades);
            grid.Children.Add(tPartidesGuanyades);
            grid.Children.Add(tPartidesPerdudes);
            grid.Children.Add(tCoeficient);

            Grid.SetColumn(tPosicio, 0);
            Grid.SetRow(tPosicio, 0);
            Grid.SetColumn(tNomJugador, 1);
            Grid.SetRow(tNomJugador, 0);
            Grid.SetColumn(tPartidesJugades, 2);
            Grid.SetRow(tPartidesJugades, 0);
            Grid.SetColumn(tPartidesGuanyades, 3);
            Grid.SetRow(tPartidesGuanyades, 0);
            Grid.SetColumn(tPartidesPerdudes, 4);
            Grid.SetRow(tPartidesPerdudes, 0);
            Grid.SetColumn(tCoeficient, 5);
            Grid.SetRow(tCoeficient, 0);

            for (int i = 0; i < inscrits.Count; i++)
            {
                RowDefinition r = new RowDefinition();
                grid.RowDefinitions.Add(r);

                //Posicio
                TextBox tiPosicio = new TextBox();
                tiPosicio.IsEnabled = false;
                tiPosicio.Text = ("" +(i+1));
                grid.Children.Add(tiPosicio);
                Grid.SetColumn(tiPosicio, 0);
                Grid.SetRow(tiPosicio, i+1);

                //Nom Jugador
                TextBox tiNomJugador = new TextBox();
                tiNomJugador.IsEnabled = false;
                tiNomJugador.Text = inscrits[i].Soci.Nom;
                grid.Children.Add(tiNomJugador);
                Grid.SetColumn(tiNomJugador, 1);
                Grid.SetRow(tiNomJugador, i + 1);

                //Partides Jugades
                TextBox tiPartidesJugades = new TextBox();
                tiPartidesJugades.IsEnabled = false;
                tiPartidesJugades.Text = "" + TorneigBD.selectPartidesJugadesDeUnInscrit(idTorneig, g.Num, inscrits[i]);
                grid.Children.Add(tiPartidesJugades);
                Grid.SetColumn(tiPartidesJugades, 2);
                Grid.SetRow(tiPartidesJugades, i + 1);


                TextBox tiPartidesGuanyades = new TextBox();
                tiPartidesGuanyades.IsEnabled = false;
                tiPartidesGuanyades.Text = "" + TorneigBD.selectPartidesGuanyadesDeUnInscrit(idTorneig, g.Num, inscrits[i]); 
                grid.Children.Add(tiPartidesGuanyades);
                Grid.SetColumn(tiPartidesGuanyades, 3);
                Grid.SetRow(tiPartidesGuanyades, i + 1);

                TextBox tiPartidesPerdudes = new TextBox();
                tiPartidesPerdudes.IsEnabled = false;
                tiPartidesPerdudes.Text = "" + TorneigBD.selectPartidesPerdudesDeUnInscrit(idTorneig, g.Num, inscrits[i]); ;
                grid.Children.Add(tiPartidesPerdudes);
                Grid.SetColumn(tiPartidesPerdudes, 4);
                Grid.SetRow(tiPartidesPerdudes, i + 1);

                TextBox tiCoeficient = new TextBox();
                tiCoeficient.IsEnabled = false;
                tiCoeficient.Text = "" + TorneigBD.selectCoeficientDeUnInscrit(inscrits[i]);
                grid.Children.Add(tiCoeficient);
                Grid.SetColumn(tiCoeficient, 5);
                Grid.SetRow(tiCoeficient, i + 1);

            }

            return grid;
        }
    }
}
