﻿#pragma checksum "E:\Cristian Lopez\Proyecto\UWP_Gestio_Tornejos\Tornejos\Tornejos\MainPage.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "1EC2086387D44A90353153F05785FD55"
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
                    this.textBlock1 = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 4:
                {
                    this.lvTornejos = (global::Windows.UI.Xaml.Controls.ListView)(target);
                    #line 119 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ListView)this.lvTornejos).SelectionChanged += this.lvTornejos_SelectionChanged;
                    #line default
                }
                break;
            case 5:
                {
                    this.lvGrups = (global::Windows.UI.Xaml.Controls.ListView)(target);
                }
                break;
            case 6:
                {
                    this.btnFiltreData = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 121 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnFiltreData).Click += this.btnFiltreData_Click;
                    #line default
                }
                break;
            case 7:
                {
                    this.btnEsborrar = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 122 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnEsborrar).Click += this.btnEsborrar_Click;
                    #line default
                }
                break;
            case 8:
                {
                    this.btnTancar = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 123 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnTancar).Click += this.btnTancar_Click;
                    #line default
                }
                break;
            case 9:
                {
                    this.btnFiltreEstat = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 124 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnFiltreEstat).Click += this.btnFiltreEstat_Click;
                    #line default
                }
                break;
            case 10:
                {
                    this.btnEliminarFiltres = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 125 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnEliminarFiltres).Click += this.btnEliminarFiltres_Click;
                    #line default
                }
                break;
            case 11:
                {
                    this.textBlockGrupName = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 12:
                {
                    this.textBlockCaramboles = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 13:
                {
                    this.textBlockEntrades = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 14:
                {
                    this.txtNomGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 15:
                {
                    this.txtCarambolesGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 16:
                {
                    this.txtLimitEntradesGrup = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 17:
                {
                    this.btnCrearGrup = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 89 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnCrearGrup).Click += this.btnCrearGrup_Click;
                    #line default
                }
                break;
            case 18:
                {
                    this.textBlockListaInscritos = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 19:
                {
                    this.lvInscrits = (global::Windows.UI.Xaml.Controls.ListView)(target);
                    #line 91 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.ListView)this.lvInscrits).SelectionChanged += this.lvInscrits_SelectionChanged;
                    #line default
                }
                break;
            case 20:
                {
                    this.textBlockGrupName_Copy = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 21:
                {
                    this.lvGrupsDisponibles = (global::Windows.UI.Xaml.Controls.ListView)(target);
                }
                break;
            case 22:
                {
                    this.btnAfegirInscritAGrup = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 94 "..\..\..\MainPage.xaml"
                    ((global::Windows.UI.Xaml.Controls.Button)this.btnAfegirInscritAGrup).Click += this.btnAfegirInscritAGrup_Click;
                    #line default
                }
                break;
            case 23:
                {
                    this.txbTitol = (global::Windows.UI.Xaml.Controls.TextBox)(target);
                }
                break;
            case 24:
                {
                    this.textBlock = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 25:
                {
                    this.textBlock_Copy = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 26:
                {
                    this.cmbModalitats = (global::Windows.UI.Xaml.Controls.ComboBox)(target);
                }
                break;
            case 27:
                {
                    this.textBlock_Copy1 = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 28:
                {
                    this.dtpDataTorneig = (global::Windows.UI.Xaml.Controls.DatePicker)(target);
                }
                break;
            case 29:
                {
                    this.btnCrearTorneig = (global::Windows.UI.Xaml.Controls.Button)(target);
                    #line 77 "..\..\..\MainPage.xaml"
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

