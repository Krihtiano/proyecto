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
        private Boolean data, estat;



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
            lvGrups.ItemsSource = TorneigBD.selectGrupsDeUnTorneig(t.Id);
        }
    }
}
