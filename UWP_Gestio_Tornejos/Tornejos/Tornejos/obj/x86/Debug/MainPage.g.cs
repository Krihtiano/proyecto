﻿#pragma checksum "E:\Cristian Lopez\Proyecto\UWP_Gestio_Tornejos\Tornejos\Tornejos\MainPage.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "A9048320C6C0B7FF7DA52255977B85D8"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Tornejos
{
    partial class MainPage : 
        global::Windows.UI.Xaml.Controls.Page, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 14.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1:
                {
                    global::Windows.UI.Xaml.Controls.Button element1 = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 19 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)element1).Click += this.btnClassificacio_Click;
                    #line default
                }
                break;
            case 2:
                {
                    global::Windows.UI.Xaml.Controls.Button element2 = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 22 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)element2).Click += this.btnEntradaResultats_Click;
                    #line default
                }
                break;
            case 3:
                {
                    this.Pivote = (global::Windows.UI.Xaml.Controls.Pivot)(target);
                }
                break;
            case 4:
                {
                    this.textBlock1 = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 5:
                {
                    this.lvTornejos = (global::Windows.UI.Xaml.Controls.ListView)(target);
                    #line 124 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ListView)this.lvTornejos).SelectionChanged += this.lvTornejos_SelectionChanged;
                    #line default
                }
                break;
            case 6:
                {
                    this.lvGrups = (global::Windows.UI.Xaml.Controls.ListView)(target);
                }
                break;
            case 7:
                {
                    this.btnFiltreData = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 126 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnFiltreData).Click += this.btnFiltreData_Click;
                    #line default
                }
                break;
            case 8:
                {
                    this.btnEsborrar = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 127 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnEsborrar).Click += this.btnEsborrar_Click;
                    #line default
                }
                break;
            case 9:
                {
                    this.btnTancar = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 128 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnTancar).Click += this.btnTancar_Click;
                    #line default
                }
                break;
            case 10:
                {
                    this.btnFiltreEstat = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 129 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnFiltreEstat).Click += this.btnFiltreEstat_Click;
                    #line default
                }
                break;
            case 11:
                {
                    this.btnEliminarFiltres = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 130 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnEliminarFiltres).Click += this.btnEliminarFiltres_Click;
                    #line default
                }
                break;
            case 12:
                {
                    this.Classificacio = (global::Windows.UI.Xaml.Controls.PivotItem)(target);
                }
                break;
            case 13:
                {
                    this.Resultats = (global::Windows.UI.Xaml.Controls.PivotItem)(target);
                }
                break;
            case 14:
                {
                    this.lvClassificacioGrups = (global::Windows.UI.Xaml.Controls.ListView)(target);
                }
                break;
            case 15:
                {
                    this.textBlockGrupName = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 16:
                {
                    this.textBlockCaramboles = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 17:
                {
                    this.textBlockEntrades = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 18:
                {
                    this.txtNomGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 19:
                {
                    this.txtCarambolesGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 20:
                {
                    this.txtLimitEntradesGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 21:
                {
                    this.btnCrearGrup = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 91 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnCrearGrup).Click += this.btnCrearGrup_Click;
                    #line default
                }
                break;
            case 22:
                {
                    this.textBlockListaInscritos = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 23:
                {
                    this.lvInscrits = (global::Windows.UI.Xaml.Controls.ListView)(target);
                    #line 93 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ListView)this.lvInscrits).SelectionChanged += this.lvInscrits_SelectionChanged;
                    #line default
                }
                break;
            case 24:
                {
                    this.textBlockGrupName_Copy = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 25:
                {
                    this.lvGrupsDisponibles = (global::Windows.UI.Xaml.Controls.ListView)(target);
                }
                break;
            case 26:
                {
                    this.btnAfegirInscritAGrup = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 96 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnAfegirInscritAGrup).Click += this.btnAfegirInscritAGrup_Click;
                    #line default
                }
                break;
            case 27:
                {
                    this.btnTancarGrups = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 97 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnTancarGrups).Click += this.btnTancarGrups_Click;
                    #line default
                }
                break;
            case 28:
                {
                    this.btnGenerarEncreuaments = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 98 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnGenerarEncreuaments).Click += this.btnGenerarEncreuaments_Click;
                    #line default
                }
                break;
            case 29:
                {
                    this.txbTitol = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 30:
                {
                    this.textBlock = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 31:
                {
                    this.textBlock_Copy = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 32:
                {
                    this.cmbModalitats = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                }
                break;
            case 33:
                {
                    this.textBlock_Copy1 = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 34:
                {
                    this.dtpDataTorneig = (global::Windows.UI.Xaml.Controls.DatePicker)(target);
                }
                break;
            case 35:
                {
                    this.btnCrearTorneig = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 79 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnCrearTorneig).Click += this.btnCrearTorneig_Click;
                    #line default
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 14.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}

