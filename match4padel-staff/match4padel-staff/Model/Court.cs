using match4padel_staff.Model.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace match4padel_staff.Model
{
    public class Court
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string PictureUrl { get; set; }
        public CourtZone CourtZone { get; set; }
        public CourtType CourtType { get; set; }
        public decimal PricePerMatch { get; set; }
        public string PicturePath
        {
            get
            {
                return $"../../Images/Courts/{PictureUrl}";
            }
        }
    }
}
