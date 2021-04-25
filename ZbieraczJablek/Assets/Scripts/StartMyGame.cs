using UnityEngine;
using UnityEngine.SceneManagement;

public class StartMyGame : MonoBehaviour
{
    public void OnButtonPress()
    {
       SceneManager.LoadScene("SampleScene");
    }
}
