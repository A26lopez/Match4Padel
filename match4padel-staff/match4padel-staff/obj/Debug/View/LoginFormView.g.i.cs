// Updated by XamlIntelliSenseFileGenerator 21/05/2025 16:14:04
#pragma checksum "..\..\..\View\LoginFormView.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "83BC1338185C22F00C8EB0EFE91993B5D11A35FA0CB5CB0331445531D012DBB4"
//------------------------------------------------------------------------------
// <auto-generated>
//     Este código fue generado por una herramienta.
//     Versión de runtime:4.0.30319.42000
//
//     Los cambios en este archivo podrían causar un comportamiento incorrecto y se perderán si
//     se vuelve a generar el código.
// </auto-generated>
//------------------------------------------------------------------------------

using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;
using match4padel_staff.View;


namespace match4padel_staff.View
{


    /// <summary>
    /// LoginFormView
    /// </summary>
    public partial class LoginFormView : System.Windows.Controls.UserControl, System.Windows.Markup.IComponentConnector
    {

        private bool _contentLoaded;

        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        public void InitializeComponent()
        {
            if (_contentLoaded)
            {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/match4padel-staff;component/view/loginformview.xaml", System.UriKind.Relative);

#line 1 "..\..\..\View\LoginFormView.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);

#line default
#line hidden
        }

        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target)
        {
            this._contentLoaded = true;
        }
        internal System.Windows.Controls.Grid login_form_grid;
        internal System.Windows.Controls.ContentControl logo;
        internal System.Windows.Controls.TextBox username_textBox;
        internal System.Windows.Controls.PasswordBox password_textBox;
        internal System.Windows.Controls.TextBlock rememberPassword;
        internal System.Windows.Controls.TextBlock loginError;
        internal System.Windows.Controls.Button login_button;
        internal System.Windows.Controls.TextBlock signUp;
    }
}

