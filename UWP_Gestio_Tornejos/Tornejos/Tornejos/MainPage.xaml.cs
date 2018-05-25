using System;
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

        }

        private void btnEntradaResultats_Click(object sender, RoutedEventArgs e)
        {

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
    }
}
