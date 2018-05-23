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
            }
            else
            {
                lvGrups.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
            }
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
                DisplayInvalidTorneigName();
            }

            if(cmbModalitats.SelectedItem == null)
            {
                DisplayInvalidModalitat();
            }



        }

        private void btnEliminarFiltres_Click(object sender, RoutedEventArgs e)
        {
            lvTornejos.ItemsSource = TorneigBD.selectTornejos();
            btnFiltreData.Content = "Data Ascendent";
            btnFiltreEstat.Content = "Estat Obert";
        }

        private async void DisplayInvalidTorneigName()
        {
            ContentDialog noWifiDialog = new ContentDialog
            {
                Title = "Titol incorrecte",
                Content = "El nom del Torneig es incorrecte (2-30) caracters",
                PrimaryButtonText = "Ok"
            };

            ContentDialogResult result = await noWifiDialog.ShowAsync();
        }

        private async void DisplayInvalidModalitat()
        {
            ContentDialog noWifiDialog = new ContentDialog
            {
                Title = "Modalitat incorrecta",
                Content = "La modalitat es incorrecta",
                PrimaryButtonText = "Ok"
            };

            ContentDialogResult result = await noWifiDialog.ShowAsync();
        }
    }
}
