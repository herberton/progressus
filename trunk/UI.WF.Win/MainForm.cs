using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace UI.WF.Win
{
    public partial class MainForm : Form
    {
        #region Construtores
        
        public MainForm()
        {
            InitializeComponent();
        } 

        #endregion

        #region Eventos
        
        private void MainForm_Resize(object sender, EventArgs e)
        {
            this.Redimensionar();
        }

        #endregion

        #region Metodos

        private void Redimensionar()
        {
            try
            {

            }
            catch (Exception e)
            {
                throw new Utility.GenericException.Exception<Exception>(e);
            }
        } 

        #endregion
    }
}
