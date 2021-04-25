using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AppleTree : MonoBehaviour
{
    [Header("Definiowane w panelu inspekcyjym")]
    public GameObject applePrefab;
    public float speed = 1f;
    public float leftAndRigtEdge = 10f;
    public float chanceToChangeDirections = 0.1f;
    public float secondsBetweenAppleDrops = 1f;

    // Start is called before the first frame update
    void Start()
    {
        Invoke("DropApple", 2f);
    }

    // Update is called once per frame
    void Update()
    {
        Vector3 pos = transform.position;
        pos.x += speed * Time.deltaTime;
        transform.position = pos;

        if(pos.x < -leftAndRigtEdge)
        {
            speed = Mathf.Abs(speed); //ruch w prawo
        }else if (pos.x > leftAndRigtEdge)
        {
            speed = -Mathf.Abs(speed); //ruch w lewo
        }
    }

    void FixedUpdate()
    {
        // z uwagi na ró¿n¹ wydajnoœæ sprzêtu na jakim bêdzie mo¿na graæ zmiana kierunku poruszania powinna odbywaæ siê w tej metodzie
        if (Random.value < chanceToChangeDirections)
        {
            speed *= -1; // zmiana kierunku poruszania siê
        }
    }

    void DropApple()
    {
        GameObject apple = Instantiate<GameObject>(applePrefab);
        apple.transform.position = transform.position;
        Invoke("DropApple", secondsBetweenAppleDrops);
    }
}
