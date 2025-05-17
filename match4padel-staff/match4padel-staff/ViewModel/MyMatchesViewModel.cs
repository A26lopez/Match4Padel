using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace match4padel_staff.ViewModel
{
    public class MyMatchesViewModel : BaseViewModel
    {
        public ObservableCollection<Match> Matches { get; set; }

        private readonly MatchService matchService;

        public MyMatchesViewModel()
        {
            matchService = new MatchService();
            Matches = new ObservableCollection<Match>();
            LoadMatches();
        }

        private async Task LoadMatches()
        {
            var result = await matchService.getMatchesByUserId(SessionService.Instance.UserId);
            if (result is List<Match> matchList)
            {
                foreach (var m in matchList)
                {
                    Matches.Add(m);
                }
            }
        }
    }
}
